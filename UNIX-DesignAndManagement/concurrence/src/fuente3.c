#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>

#define MAX 100   //tamaño maximo de la cadena

int main(void) {
  printf("Proceso P3 (PID=%d, Ej3): carga region de memoria compartida\n", getpid());
  int keyMen      = ftok("fichero1", 'z'),
      id_semaforo = semget(keyMen, 1, 0666),
      id_memoria  = shmget(keyMen, MAX * sizeof(int), SHM_R | SHM_W),
      *vc1        = (int *) shmat(id_memoria, 0, 0); //casteamos

  printf("Proceso P3 (PID=%d, Ej3): esperando a que el semaforo de P2 libere la Memoria\n", getpid());
  while (semctl(id_semaforo, 0, GETVAL, 0) > 0);
  printf("Proceso P3 (PID=%d, Ej3): ve el semaforo liberado. Leyendo mensaje de P2: \n", getpid());

  char mensaje[MAX];
  int longitud = vc1[0];
  printf("Proceso P3 (PID=%d, Ej3): mensaje: ", getpid());

  for (int i = 0; i < longitud; i++) {
    mensaje[i] = (char) vc1[i];
  }

  mensaje[longitud + 1] = '\0';
  printf("%s\n", mensaje);

  shmdt(0);                         //liberamos la memoria compartida
  semctl(id_semaforo, 0, IPC_RMID); //liberamos semaforo

  //enviamos mensaje a la cola
  printf("Proceso P2 (PID=%d, Ej3): Enviando el mensaje a la cola", getpid());

  //calculamos el pid de P3
  int P3 = getpid();

  //mensaje de la cola
  struct {
    long int tipo;
    char cadena[MAX];
  } mensajeCola;

  mensajeCola.tipo = 1;
  for (int i = 5; i <= 0; i--) {
    mensajeCola.cadena[i] = 48 + P3 % 10;	P3 /= 10;
  }

  //crear cola de mensajes
  int key   = ftok("Ej1", 'z'),
      cola  = msgget(key, 0666 | IPC_CREAT),
	    msg   = msgsnd(cola, &mensajeCola, sizeof(mensajeCola), 0);

  //a esperar la muerte
  printf("Proceso P2 (PID=%d, Ej3): Esperando la señal SIGKILL de P1", getpid());
  pause();
  return 0;
}
