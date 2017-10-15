/*Gestor de vuelos
---------------------------------
Autor: Pablo Cumpian Diaz*/

#include <stdio.h>
#include <stdbool.h>
#include "interfaz.h"

//programa principal

int main(void) {
  unsigned char opcion; //opcion de entrada
  bool seguir = true;

  /////////////////////
  //menu del programa//
  /////////////////////

  while (seguir){

    printf("\n");
    printf("------------------------------------------\n");
    printf("Gestion de vuelos\n");
    printf("------------------------------------------\n");
    printf("\tAlta Nuevo Vuelo\t(Pulsar A)\n");
    printf("\tOfertas del Mes\t\t(Pulsar O)\n");
    printf("\tComprar Plazas\t\t(Pulsar C)\n");
    printf("\tVuelos del Mes\t\t(Pulsar V)\n");
    printf("\tSalir\t\t\t(Pulsar S)\n");
    printf("------------------------------------------\n");
    printf("\n");

    printf("Teclee una opcion valida (A|O|C|V|S): ");
    scanf("%c", &opcion);
    printf("\n");
    switch (opcion) {
      case 'A':
        dar_alta_nuevo_vuelo();
        break;
      case 'O':
        mostrar_ofertas_del_mes();
        break;
      case 'C':
        comprar_plazas();
        break;
      case 'V':
        mostrar_vuelos_del_mes();
        break;
      case 'S':
        return seguir = false;
    }
  }
  return 0;
}
