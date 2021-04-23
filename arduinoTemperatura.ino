/**
 * Modulo Temperatura Arduino
 * @author Rebeca Pérez Carrasco; Num.Exp: 21542824
 *
 */

#include <LiquidCrystal.h>
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);


#include <DHT.h>
#define DHTTYPE DHT11
const int DHTPin = 13; 
DHT dht(DHTPin, DHTTYPE);

String sensor = "";
String orden  = "";
String input  = "";
char caracter;
boolean automatico = true;

void setup() {
  //LCD Columnas y filas
  lcd.begin(16, 2);
  //Inicializar LCD
  lcd.setCursor(0,0);
  lcd.print("Tmp: -- Hmd: --");
 
  //Inicializar salidar leds colores
  pinMode(9, OUTPUT);
  pinMode(8, OUTPUT);
 
  Serial.begin(9600);
  
}

  void moduloCalentadorVentilador(String modulo){

          //Imprimir temperatura y humedad
          int h   = dht.readHumidity();
          int tmp = dht.readTemperature();
          lcd.setCursor(5, 0);
          lcd.print(tmp);
          lcd.setCursor(13, 0);
          lcd.print(h);
          
          //Inicializo los LEDs
          digitalWrite(9, LOW);
          digitalWrite(8, LOW);
          digitalWrite(7, LOW);
          lcd.setCursor(0, 1);
/*
encender calentador= luz roja
temp min=18ºC
temp max=20ºC

encender ventilador = luz azul
temp max= 28ºC ó humedad=50%
temp min =26ºC ó humedad=40%
*/             
          //Encender LED en función del estado
          if( tmp < 20  ){
            digitalWrite(9, HIGH); //Encendemos luz roja
            lcd.print("Calentador ON   ");
          }else if(tmp > 26 ){
            digitalWrite(8, HIGH);       
            lcd.print("Ventilador ON   "); 
          }else if(h > 40){
            digitalWrite(7, HIGH);
            lcd.print("Ventilador ON   "); 
          }else{
            lcd.print("OFF "+modulo); 
          }
    }
    
void loop() {

    

      //Extraemos la cadena de caracteres enviada desde java en el input
      while (Serial.available()){
        caracter = Serial.read();  
        input.concat(caracter);
      }        

      // Extraemos los datos divididos por el .
      for (int i = 0; i < input.length(); i++) {
        if (input.substring(i, i+1) == ".") {
          orden  = input.substring(0, i);
          sensor = input.substring(i+1);
        }
      }
     
      //El arduino funciona segun la orden enviada
      if (orden.equals("0000") ){
        automatico = false;
      }

      if (orden.equals("0111")){
        automatico = true;
      }

       if (!automatico){
            if ( sensor.equals("2101")){
               moduloCalentadorVentilador("Calentador");//Encendemos modulo del calentador    
               Serial.println("Modulo Calentador");
              }

            if ( sensor.equals("3101")){
               moduloCalentadorVentilador("Ventilador");//Encendemos modulo del ventilador   
               Serial.println("Modulo Ventilador"); 
            }
           input=""; //Borramos el input para recibir el siguiente input
       }else{
              int tmp = dht.readTemperature();
              if (tmp >= 25){
                  moduloCalentadorVentilador("Vent. auto");//Encendemos modulo del ventilador   
                  Serial.println("Modulo tmp automatico ON"); 
                }else{
                   moduloCalentadorVentilador("Calent. auto");//Encendemos modulo del calentador    
                   Serial.println("Modulo tmp automatico ON");
                }             
        }
       
      
      input=""; //Borramos el input para recibir el siguiente input
      delay(1000);
}
  
