#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <fcntl.h>
#include <sys/shm.h>
#include <sys/sem.h>

#define MAX 100   //tamaño maximo de la cadena

int main(void) {
  char mensaje[MAX];
  int longitud,
      fichero = open("fichero1", O_RDWR | O_NONBLOCK);

  longitud = read(fichero, mensaje, MAX);
  mensaje[longitud] = '\0';
  printf("Proceso P2 (PID=%d, Ej2): esta leyendo mensaje de FIFO: \n", getpid());

  //memoria compartida
  printf("Proceso P2 (PID=%d, Ej2): Creando region de Memoria compartida\n", getpid());
  int key         = ftok("fichero1", 'z'),
      id_semaforo = semget(key, 1, 0777 | IPC_CREAT),
      id_memoria  = shmget(key, MAX * sizeof(int), IPC_CREAT | 0777),
      *vc1        = (int *) shmat(id_memoria, 0, 0);

  semctl(id_semaforo, 0, SETVAL, 0);  // inicializa Semaforo a 0.
  struct sembuf op_V = {0, +1, 0};    // sumar uno al semaforo : V())
  struct sembuf op_P = {0, -1, 0};    // restar uno al semaforo : P())
  semop(id_semaforo, &op_V, 1);

  /* Division en 2: fork */
  printf("Proceso P2 (PID=%d, Ej2): Bifurcacion en P2 y P3\n", getpid());
  pid_t pid_hijo = fork();

  if (pid_hijo !=0) {  //padre. P2
    //ponemos a esperar al proceso un segundo
    printf("Proceso P2 (PID=%d, Ej2): PAUSADO: 1seg\n", getpid());
    sleep(1);

    //escribimos mensaje en memoria compartida
    printf("Proceso P2 (PID=%d, Ej2): Escribe Mensaje en Memoria Compartida\n", getpid());
    vc1[0] = longitud;
    for (int i = 0; i < longitud; i++){
      vc1[i + 1] = (int) mensaje[i];
    }

    //abrimos el semaforo
    printf("Proceso P2 (PID=%d, Ej2): Abre semaforo ...PAUSADO: ESPERANDO SIGKILL DE P1...\n", getpid());
    semop(id_semaforo, &op_P, 1);
    pause();

} else {  //hijo3
    execv("./Ej3", NULL);
  }
  return 0;
}
