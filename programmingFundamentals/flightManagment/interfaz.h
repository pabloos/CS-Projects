//Modulo de interfaz

#include <stdint.h>

//constantes
#define MAX_VUELOS  50 //maximo de vuelos que se pueden reservar al mes
#define MAX_MESES   24 //maximo de meses que se pueden gestionar (2 annos)

//tipos
typedef char Tipo_Codigo[7];

typedef struct Tipo_Vuelo{
  Tipo_Codigo           codigo;
  unsigned short int    anno;
  unsigned short int    mes;
  unsigned short int    dia;
  Tipo_Codigo           hora;
  unsigned short int    plazas;
  unsigned short int    plazas_iniciales;
  float  precio;
  float  precio_inicial;
}Tipo_Vuelo;

typedef Tipo_Vuelo Tipo_Vector[MAX_VUELOS];
typedef Tipo_Vector Tipo_Matriz[MAX_MESES];

//variables
extern Tipo_Matriz matriz;
extern unsigned short int volcado;

//interfaz de las funciones
void dar_alta_nuevo_vuelo(void);
void mostrar_ofertas_del_mes(void);
void comprar_plazas(void);
void mostrar_vuelos_del_mes(void);
