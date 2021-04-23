/*El esquema sería el siguiente:
 * 1-Nivel de luz baja + movimiento: Encender luz y activar servo.
 * 2-Nivel de luz alto: Luz apagada (Por defecto).
 * 3-Nivel de luz baja - movimiento: Luz apagada (Por defecto) servos abajo.
 * 4-Dejaremos para después el tema de Movimiento + alarma activa: Activar zumbador.

*/
//Cabeceras
#include <Servo.h>
//Falta añadir la cabecera del LDR para alguna funcionalidad

//Declaración del servo
Servo servo1;
//Declaracion de LDR
const int LEDPin = 13;
const int LDRPin = A0;
const int threshold = 100;
//Declaracion de Sensor movimiento
const int LEDPIN = 13; //Repetido con el de LDR
const int PIRPin = 2; //Pin de entrada (Para el sensor PIR)


//Declaramos el inicio del sistema de movimiento para que empiece sin nada
int pirState = LOW; //De inicio no hay movimiento
int val= 0; //estado del pin

//Empezamos la ejecución
void setup() {
   pinMode(LEDPin, OUTPUT); //LED
   pinMode(LDRPin, INPUT); //LDR
   pinMode(PIRPin, INPUT); //PIR
   //serial.begin(9600);
   servo1.attach(10); //SERVO
}
 
void loop() {
    //Función general
    int input =analogRead(LDRPin);
    val = digitalRead(PIRPin);

    //Funcion 1 (Si luz baja y movimiento)
    if(input < threshold,val == HIGH){
      if (LEDPin == HIGH) // Si previamente estaba encendido el LED
    {
      Serial.println("LED encendido");
      servo1.write(360);
    }
    }
    else{
      digitalWrite(LEDPin,HIGH);
      servo1.write(360);
    }

    
    //Funcion 2  (Si luz alta)
    if(input > threshold){
      if (LEDPin ==LOW) //Si previamente estaba apagado
    {
      Serial.println("LED Apagado");
      servo1.write(-360);
    }
    }
    else{
      digitalWrite(LEDPin, LOW);
      servo1.write(-360);
    }

    
    //Funcion 3 (Si luz baja y no movimiento)
    if(input < threshold,val ==LOW){ 
      if (LEDPin ==LOW) //Si previamente estaba apagado
    {
      Serial.println("LED apagado");
      servo1.write(-360);
    }
    else{
      digitalWrite(LEDPin, LOW);
      servo1.write(-360);
    }
    }
}



/*

Aquí dejo las funciones separadas por si queremos añadir funcionalidades
  
  //Sensor LDR
   int input = analogRead(LDRPin);
   if (input > threshold) {
      digitalWrite(LEDPin, HIGH);
   }
   else {
      digitalWrite(LEDPin, LOW);
   }

   
  //Sensor PIR
  val = digitalRead(PRIPin);
  if (val == HIGH) //Si está activado
  {
    digitalWrite(LEDPin, HIGH);
    if (pirState ==LOW) //Si previamente estaba apagado
    {
      Serial.println("Sensor activado");
      pirState = HIGH;
    }
  }
  else //Si esta desactivado
  {
    digitalWrite(LEDPin, LOW); //LED OFF
    if (pirState == HIGH) // Si previamente estaba encendido
    {
      Serial.println("Sensor parado");
      pirState = LOW;
    }
  }
}
  
   //Funcion de los servos (Subir)
  servo1.write(360);
  //Funcion de los servos (Bajar)
  servo1.write(-360)
  //Habría que poner los grados justos para que recogiera la persiana completa.
}

*/
