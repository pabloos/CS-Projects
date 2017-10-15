#número de observaciones por variable (ambas suman 50000)
obs <- 25000;

#cuantil a aproximar
cuantil <- 1.645;

#funcion para la simulación
simul <- function(obs){
    z <-matrix(data=0, nr=obs, nc=2)

    for (i in 1:obs){
        #generamos un valor aleatorio
        u <- runif(2);
        
        #rellenamos de forma iterativa las posiciones de la matriz con datos devueltos por la fórmula de Box-Muller
        z[i,1] <- sqrt(-2*log(u[1]))*cos(2*pi*u[2]);
        z[i,2] <- sqrt(-2*log(u[1]))*sin(2*pi*u[2]);
    }

    return(z);
}

#la función devuelve la matriz de valores pasándole como parámetro el numero de observaciones por variable aleatoria
z <- simul(obs);

#llamamos a la función hist() para imprimir la función
hist(z, breaks=seq(-5,5,by=0.01), col="blue", freq=T,
    main="Histograma de distribucion N(0,1) mediante Box-Muller",
    xlab="variable aleatoria Z",
    ylab="Frecuencia");

#variable que almacena el valor de la probabilidad almacenada
prob = length(z[z>cuantil]) / length(z);

print(prob);

