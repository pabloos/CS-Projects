#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/msg.h>
#include <sys/times.h>
#include <fcntl.h>
#include <signal.h>

#define LEER			0    //descriptor para leer en la tuberia con nombre
#define ESCRIBIR	1    //descriptor para escribir en la tuberia con nombre
#define MAX			  100  //tamaño maximo de la cadena

int main(void) {
	//creamos tuberia sin nombre
  int tuberia[2];
  pipe(tuberia);

  pid_t P2 = fork(); //creamos el proceso P2

  if (P2 != 0) {  //Proceso 2
    struct tms st_cpu;
    clock_t st_time = times(&st_cpu);

    close(tuberia[LEER]); //solo escribimos

    //solicitamos el mensaje al usuario
    char mensaje[MAX];
    printf("Proceso P1 (PID=%d, Ej1): Introduzca el mensaje: ", getpid());
    fgets(mensaje, MAX, stdin);

    //enviamos el mensaje por la tuberia
    printf("Proceso P1 (PID=%d, Ej1): envia el mensaje por tuberia sin nombre a P2\n", getpid());
    write(tuberia[ESCRIBIR], mensaje, (strlen(mensaje)));

    //creamos la cola de mensajes
    printf("Proceso P1 (PID=%d, Ej1): creando cola de mensajes\n", getpid());
    int key = ftok("Ej1", 'z'),
        cola= msgget(key, 0666 | IPC_CREAT);

    //nos mantenemos a la espera del mensaje de P3 por la cola
    printf("Proceso P1 (PID=%d, Ej1): ESPERANDO a la cola de mensajes\n", getpid());
    
    struct {
      long int tipo;
      char cadena[MAX];
    } mensajeCola;

    int recibidos = 0;
    while (recibidos <= 0) {
      recibidos = msgrcv(cola, &mensajeCola, sizeof (mensajeCola), 1, 0);
    }

    //recibiendo pid del proceso 3 por la cola de mensajes
    int P3 =    (((((mensajeCola.cadena[0] - 48) * 10 +
                    (mensajeCola.cadena[1] - 48)) * 10 +
                    (mensajeCola.cadena[2] - 48)) * 10 +
                    (mensajeCola.cadena[3] - 48)) * 10 +
                    (mensajeCola.cadena[4] - 48)) * 10 +
                    (mensajeCola.cadena[5] - 48);

    printf("Proceso P1 (PID=%d, Ej1): extrae el PID que envia P3\n", getpid());

    //Envio de SIGKILL a P2 y a P3
    printf("Proceso P1 (PID=%d, Ej1): matando P2 y P3\n", getpid());
    kill(P2, SIGKILL);
    kill(P3, SIGKILL);

    //Borramos el fichero FIFO
    unlink("fichero1");

    //Estadisticas del tiempo utilizados (time ...)
    struct tms en_cpu;
    clock_t en_time = times(&en_cpu);
    printf("\nESTADISTICAS DE EJECUCION\n-------------------------\n");
    printf("Tiempo Real:\t\t%d\nTiempo de usuario\t%d\nTiempo del sistema\t%d\n\n",
			(int) (en_time - st_time),
      (int) (en_cpu.tms_utime - st_cpu.tms_utime),
      (int) (en_cpu.tms_stime - st_cpu.tms_stime));

  } else { //Proceso P2
		char mensaje[MAX];

    close(tuberia[ESCRIBIR]); //cerramos el indidcador de escritura en la tuberia

    //leemos el mensaje de la tuberia
    int longitud = read(tuberia[LEER], mensaje, MAX);
    printf("Proceso P2 (PID=%d, Ej1): Mensaje Recibido\n", getpid()); //imprimir mensaje

    //creamos el fichero FIFO y almacenamos el mensaje
    printf("Proceso P2 (PID=%d, Ej1): escribiendo en el fichero FIFO\n", getpid());
    mknod("fichero1", S_IFIFO | 0666, 0);
    int fichero = open("fichero1", O_RDWR | O_NONBLOCK);
    write(fichero, mensaje, longitud);

    //Ejecuta Ej2
    execv("./Ej2", NULL);
  }
  return 0;
}
