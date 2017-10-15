/*Modulo de implementacion*/

//Modulos añadidos
#include <stdio.h>
#include <string.h>
#include "interfaz.h"

//Macros
#ifndef MAX_VUELOS
#define 50
#endif

#ifndef MAX_MESES
#define 24
#endif

short int cont = -1;
Tipo_Matriz matriz;

void dar_alta_nuevo_vuelo(void) {
  char cadena[7];
  unsigned short int mes, volcado;
  float precio_volcado;

  cont = cont + 1;


  //Introduccion de los datos por el usuario
  printf("Introduzca el mes del vuelo: \n");
  scanf("%hu", &mes);
  mes = mes-1;
  matriz[cont][mes].mes = mes;

  printf("Introduzca el codigo del vuelo: \n");
  scanf("%s", cadena);
  strcpy(matriz[cont][mes].codigo, cadena);

  printf("Introduzca la hora del vuelo (hh:mm): \n");
  scanf("%s", cadena);
  strcpy(matriz[cont][mes].hora, cadena);

  printf("Introduzca el dia del vuelo: \n");
  scanf("%hu", &volcado);
  matriz[cont][mes].dia = volcado;

  printf("Introduzca las plazas del vuelo: \n");
  scanf("%hu", &volcado);
  matriz[cont][mes].plazas = volcado;
  matriz[cont][mes].plazas_iniciales = volcado;

  printf("Introduzca el precio del vuelo: \n");
  scanf("%f", &precio_volcado);
  matriz[cont][mes].precio = precio_volcado;
  matriz[cont][mes].precio_inicial = precio_volcado;

  printf("Introduzca el anno (2016 o 2017): \n");
  scanf("%hd", &volcado);
  matriz[cont][mes].anno = volcado;
}

void mostrar_ofertas_del_mes(void) {
  int mes;
  Tipo_Vuelo aux;

  printf("Introduzca el mes: \n");
  scanf("%d", &mes);

  mes = mes - 1;

  for (int i = 0; i < MAX_VUELOS; ++i) {
      for (int j = i + 1; j < MAX_VUELOS; ++j) {
          if (matriz[i][mes].precio > matriz[j][mes].precio) {
              aux = matriz[i][mes];
              matriz[i][mes] = matriz[j][mes];
              matriz[j][mes] = aux;
          }
      }
  }

  for (int i = 0; i < 5; i++) {
    printf("Codigo:\t%s\nPrecio:\t%f\nDia:\t%d\nHora:\t%s\n\n", matriz[i][mes].codigo, matriz[i][mes].precio, matriz[i][mes].dia, matriz[i][mes].hora);
  }
}

void comprar_plazas(void) {
  int dia, mes, anno, numero, plazas;
  Tipo_Vuelo aux;

  printf("Introduzca el dia: \n");
  scanf("%d", &dia);

  printf("Introduzca el mes: \n");
  scanf("%d", &mes);

  printf("Introduzca el anno: \n");
  scanf("%d", &anno);

  mes = mes - 1;

  //impresion
  printf("-----------------------------------------------\n");
  printf("Vuelo\tCodigo\tHora\tPlazas\tPrecio\n");
  printf("-----------------------------------------------\n");

  if (anno == 2016) {
    for (int i = 0; i < MAX_VUELOS; ++i) {
        for (int j = i + 1; j < MAX_VUELOS; ++j) {
            if (matriz[i][mes].precio > matriz[j][mes].precio) {
                aux = matriz[i][mes];
                matriz[i][mes] = matriz[j][mes];
                matriz[j][mes] = aux;
            }
        }
    }

    printf("1\t%s\t%s\t%d\t%f\n", matriz[0][mes].codigo, matriz[0][mes].hora, matriz[0][mes].plazas, matriz[0][mes].precio);
    printf("2\t%s\t%s\t%d\t%f\n", matriz[1][mes].codigo, matriz[1][mes].hora, matriz[1][mes].plazas, matriz[1][mes].precio);
    printf("3\t%s\t%s\t%d\t%f\n", matriz[2][mes].codigo, matriz[2][mes].hora, matriz[2][mes].plazas, matriz[2][mes].precio);
    printf("-----------------------------------------------\n");
  }

  else if (anno == 2017) {
    for (int i = 0; i < MAX_VUELOS; ++i) {
        for (int j = i + 1; j < MAX_VUELOS; ++j) {
            if (matriz[i][mes].precio > matriz[j][mes].precio) {
                aux = matriz[i][mes+12];
                matriz[i][mes+12] = matriz[j][mes+12];
                matriz[j][mes+12] = aux;
            }
        }
    }

    printf("1\t%s\t%s\t%d\t%f\n", matriz[0][mes+12].codigo, matriz[0][mes+12].hora, matriz[0][mes+12].plazas, matriz[0][mes+12].precio);
    printf("2\t%s\t%s\t%d\t%f\n", matriz[1][mes+12].codigo, matriz[1][mes+12].hora, matriz[1][mes+12].plazas, matriz[1][mes+12].precio);
    printf("3\t%s\t%s\t%d\t%f\n", matriz[2][mes+12].codigo, matriz[2][mes+12].hora, matriz[2][mes+12].plazas, matriz[2][mes+12].precio);
    printf("-----------------------------------------------\n");
  }

  else {
    printf("-----------------------------------------------\n");
    printf("ERROR: Introduzca el año \"2016\" o \"2017\"\n");
    printf("-----------------------------------------------\n");
  }

  printf("Seleccione numero de vuelo a elegir: \n");
  scanf("%d", &numero);

  printf("Seleccione numero de plazas (maximo 5): \n");
  scanf("%d", &plazas);


  //si el año introducido es 2016
  if (anno == 2016) {
    matriz[numero-1][mes].plazas = matriz[numero-1][mes].plazas - plazas;

    //subidas de precios de 2016 en relacion al numero de plazas
    if (matriz[numero-1][mes].plazas <= matriz[numero-1][mes].plazas_iniciales * 5 / 100 ) {
      matriz[numero-1][mes].precio = matriz[numero-1][mes].precio_inicial * 5;
    }
    else if (matriz[numero-1][mes].plazas <= matriz[numero-1][mes].plazas_iniciales * 15 / 100 ) {
      matriz[numero-1][mes].precio = matriz[numero-1][mes].precio_inicial * 3;
    }
    else if (matriz[numero-1][mes].plazas <= matriz[numero-1][mes].plazas_iniciales * 30 / 100 ) {
      matriz[numero-1][mes].precio = matriz[numero-1][mes].precio_inicial * 2;
    }
  }

  //si el año introducido es 2017
  else if (anno == 2017) {
    matriz[numero-1][mes+12].plazas = matriz[numero-1][mes+12].plazas - plazas;

    //subidas de precios de 2017 en relacion al numero de plazas
    if (matriz[numero-1][mes+12].plazas <= matriz[numero-1][mes+12].plazas_iniciales * 5 / 100 ) {
      matriz[numero-1][mes+12].precio = matriz[numero-1][mes+12].precio_inicial * 5;
    }
    else if (matriz[numero-1][mes+12].plazas <= matriz[numero-1][mes+12].plazas_iniciales * 15 / 100 ) {
      matriz[numero-1][mes+12].precio = matriz[numero-1][mes+12].precio_inicial * 3;
    }
    else if (matriz[numero-1][mes].plazas <= matriz[numero-1][mes].plazas_iniciales * 30 / 100 ) {
      matriz[numero-1][mes+12].precio = matriz[numero-1][mes+12].precio_inicial * 2;
    }
  }
}

void mostrar_vuelos_del_mes (void) {
  int diaInicial, diaUltimo; //posicion en la primera linea y ultimo dia del mes
  int contador = 1;

  int mes, anno; //mes y anno introducidos por el usuario

  printf("Introduzca el mes: ");
  scanf("%d", &mes);
  printf("Introduzca el anno (2016 o 2017): ");
  scanf("%d", &anno);

  if (anno == 2017) {
    mes = mes+12;
  }

  switch (mes-1) {
    case 0:
    case 6:
    case 23:
      diaInicial  = 5;
      diaUltimo   = 31;
      break;
    case 2:
    case 19:
      diaInicial  = 2;
      diaUltimo   = 31;
      break;
    case 3:
    case 20:
      diaInicial  = 5;
      diaUltimo   = 30;
      break;
    case 4:
    case 12:
    case 21:
      diaInicial  = 7;
      diaUltimo   = 31;
      break;
    case 5:
    case 22:
      diaInicial  = 3;
      diaUltimo   = 30;
      break;
    case 7:
    case 16:
      diaInicial  = 1;
      diaUltimo   = 31;
      break;
    case 8:
    case 17:
      diaInicial  = 4;
      diaUltimo   = 30;
      break;
    case 9:
    case 18:
      diaInicial  = 6;
      diaUltimo   = 31;
      break;
    case 1:
      diaInicial  = 1;
      diaUltimo   = 29;
      break;
    case 10:
      diaInicial  = 2;
      diaUltimo   = 30;
      break;
    case 11:
      diaInicial  = 4;
      diaUltimo   = 31;
      break;
    case 13:
      diaInicial  = 3;
      diaUltimo   = 28;
      break;
    case 14:
      diaInicial  = 3;
      diaUltimo   = 31;
      break;
    case 15:
      diaInicial  = 6;
      diaUltimo   = 30;
      break;
    default:;
}

  //imprimir cabecera del calendario
  printf("\n");
  printf("--------------------------------------------------\n");
  printf("L\tM\tM\tJ\tV\tS\tD\n");
  printf("--------------------------------------------------\n");

  //imprimir la primera linea
  for (int i = 1; i <= 7; i++) {
    if (i < diaInicial) {
      printf("\t");
    }
    else {

      //check de que existen vuelos
      if (anno == 2016) {
        for (int j = 0; j < MAX_MESES/2; j++) {
          for (int k = 0; k < MAX_VUELOS; k++) {
            if (matriz[j][k].dia == i) {
              printf("%d\t", contador);
            }
          }
        }
      }

      if (anno == 2017) {
        for (int j = MAX_MESES/2; j < MAX_MESES; j++) {
          for (int k = 0; k < MAX_VUELOS; k++) {
            if (matriz[j][k].dia == i) {
              printf("%d\t", contador);
            }
          }
        }
      }

      contador++;
    }
  }
  printf("\n");

  //imprimir el bloque central del mes
  for (int i = 1; i <=5; i++) {
    if (contador > diaUltimo) {
      break;
    }


    for (int a = 1; a<= 7; a++) {

      //check de que existen vuelos
      if (anno == 2016) {
        for (int j = 0; j < MAX_MESES/2; j++) {
          for (int k = 0; k < MAX_VUELOS; k++) {
            if (matriz[j][k].dia == i) {
              printf("%d\t", contador);
            }
          }
        }
      }

      if (anno == 2017) {
        for (int j = MAX_MESES/2; j < MAX_MESES; j++) {
          for (int k = 0; k < MAX_VUELOS; k++) {
            if (matriz[j][k].dia == i) {
              printf("%d\t", contador);
            }
          }
        }
      }

      contador++;
      if (contador > diaUltimo) {
        break;
      }
    }
  printf("\n");
  }
  printf("--------------------------------------------------\n");
  printf("\n");
}
