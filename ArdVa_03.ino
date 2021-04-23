/*
 * Programa ArdVa v0.1 para control domótico de una casa:
 *    - Uso de RTC
 *    - Uso de DHT11 (preparado para 2 sensores)
 *    - Uso de sensor PIR
 *    - Uso de sensor LDR
 * 
 * Funciones integradas: 
 *    - Control de iluminacion segun sensor PIR y LDR (1 relé)
 *    - Control de climatización según sensor DHT (2 relés)
 *    - Reenvío de información cada 10 segundos
 */

#include <EEPROM.h>           // Librería para memoria EEPROM
#include <Wire.h>
#include "RTClib.h"           // Librería para RTC
#include "DHT.h"              // Librería para sensores DHT

RTC_DS1307 rtc;               // Modelo de RTC en uso (RTC_DS1307, RTC_DS3231)
char daysOfTheWeek[7][12] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

#define DHTTYPE DHT11         // Modelo de DHT usado (DHT11, DHT22, DHT21)

const int LEDPin = 13;        // Pin 13 conectado a LED
const int PIRPin = 22;        // Pin 22 conectado al sensor PIR
const int DHT1Pin = 24;       // Pin 20 conectado al sensor DHT nº1
const int DHT2Pin = 25;       // Pin 21 conectado al sensor DHT nº2
const int rele1Pin = 26;      // Pin para activar rele
const int rele2Pin = 27;      // Pin para activar rele
const int rele3Pin = 28;      // Pin para activar rele
const int rele4Pin = 29;      // Pin para activar rele
const int LDRPin = A15;       // Pin lectura LDR
DHT dht1(DHT1Pin, DHTTYPE);   // Definicion de sensor DHT nº1 (pin, modelo) [INTERIOR]
DHT dht2(DHT2Pin, DHTTYPE);   // Definicion de sensor DHT nº2 (pin, modelo) [EXTERIOR]

int PIRState;
int pirState = LOW;           // De inicio no hay movimiento
int ldrState;                 // Para almacenar el nivel de luz
int Tx1 = -1;                  // Temporizador 1 para transmision (Tx)
int temp;                     // Temporizador generico
String orden = "";            // String para código de ordenes Tx/Rx
boolean automatico = true;    // Modo automático
float tempMax = 0;            // Temperatura máxima sensor DHT nº1
float tempMin = 100;          // Temperatura mínima sensor DHT nº1
float humMax = 0;             // Humedad máxima sensor DHT nº1
float humMin = 100;           // Humedad mínima sensor DHT nº1
int eeDelay1 = 0;             // Temporizador guardado en EEPROM


void setup() {
  Serial.begin(9600);         // Inicio de comunicaciones serie
  Serial.print("Starting ArdVa...");
  //EEPROM.put(eeDelay1, 10);   // Pone 10s en el temporizador de la EEPROM
  pinMode(LEDPin, OUTPUT);    // Pin LED definido como salida
  pinMode(PIRPin, INPUT);     // Pin PIR definido como entrada
  pinMode(DHT1Pin, INPUT);    // Pin DHT nº1 definido como entrada
  pinMode(DHT2Pin, INPUT);    // Pin DHT nº2 definido como entrada
  pinMode(rele1Pin, OUTPUT);
  pinMode(rele2Pin, OUTPUT);
  pinMode(rele3Pin, OUTPUT);
  pinMode(rele4Pin, OUTPUT);
  dht1.begin();               // Inicia sensor DHT nº1
  dht2.begin();               // Inicia sensor DHT nº2
  if (!rtc.begin()){                          // Si no inicia el rtc
    Serial.println("Couldn't find RTC");      //  avisa por puerto serie
  }
  if(!rtc.isrunning()){
    Serial.println("RTC is NOT running!!");
    rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));    // Para poner en hora en compilación
  }
  digitalWrite(rele1Pin, HIGH);
  digitalWrite(rele2Pin, HIGH);
  digitalWrite(rele3Pin, HIGH);
  digitalWrite(rele4Pin, HIGH);
  delay(1000);              // Demora dos segundos iniciando para que arranquen los sensores
  Serial.println(" OK\n");
}


void loop() {
  DateTime now = rtc.now();       // Toma valores de fecha/hora actuales
  while(Serial.available()){      // Si hay datos de entrada en puerto serie
    char o=Serial.read();         // Lee caracter a caracter
    if(o!='.'){                   // Si son distintos de '.'
      orden+=o;                   // Agregar al string orden
    }
  }
  delay(20);                      // Retardo necesario para que coja todos los caracteres como un string

  
  // Ordenes de control
  if(orden== "0000"){             // Si la orden recibida es 0000
    automatico=false;             //  entra en modo manual
    Serial.println("Modo MANUAL");
  }
  if(orden== "0111"){             // Si la orden recibida es 0111
    automatico=true;              //  entra en modo automático
    Serial.println("Modo AUTOMATICO");
  }
  if(orden== "0200"){
    EEPROM.put(eeDelay1, 3);      // Temporizador 1 EEPROM a 3s
  }
  if(orden== "0201"){
    EEPROM.put(eeDelay1, 10);     // Temporizador 1 EEPROM a 10s
  }
  /*if(orden== "0210"){
    int i=0;                      // Muestra valor del temporizador 1 de EEPROM
    EEPROM.get(eeDelay1, i);
    Serial.print("Temporizador: ");
    Serial.println(i);
  }*/
 

  // Ordenes para modo manual
  if(!automatico){
    if(orden == "1011"){              // Enciende rele 1
      digitalWrite(rele1Pin, LOW);
    }
    if(orden == "1010"){              // Apaga rele 1
      digitalWrite(rele1Pin, HIGH);
    }
    if(orden == "1021"){              // Enciende rele 2
      digitalWrite(rele2Pin, LOW);
    }
    if(orden == "1020"){              // Apaga rele 2
      digitalWrite(rele2Pin, HIGH);
    }
    if(orden == "1031"){              // Enciende rele 3
      digitalWrite(rele3Pin, LOW);
    }
    if(orden == "1030"){              // Apaga rele 3
      digitalWrite(rele3Pin, HIGH);
    }
    if(orden == "1041"){              // Enciende rele 4
      digitalWrite(rele4Pin, LOW);
    }
    if(orden == "1040"){              // Apaga rele 4
      digitalWrite(rele4Pin, HIGH);
    }
    
    if(orden == "2001"){          // Orden para envío de temperatura de DHT nº1
      serialPrintTime(now);
      Serial.print("#");
      Serial.print("2001");
      DHTtemperatura(dht1);
      Serial.println("#");
    }
    if(orden == "2101"){          // Orden para envío de temperatura de DHT nº2
      serialPrintTime(now);
      Serial.print("#");
      Serial.print("2101");
      DHTtemperatura(dht2);
      Serial.println("#");
    }
    if(orden == "2FFF"){          // Orden para envío de rango max/min de temperatura
      Serial.print("Temp.Maxima: ");
      Serial.print(tempMax);
      Serial.print("\tTemp.Minima: ");
      Serial.println(tempMin);
    }
    if(orden == "3001"){          // Orden para envío de humedad de DHT nº1
      serialPrintTime(now);
      Serial.print("#");
      Serial.print("3001");
      DHThumedad(dht1);
      Serial.println("#");
    }
    if(orden == "3101"){          // Orden para envío de humedad de DHT nº2
      serialPrintTime(now);
      Serial.print("#");
      Serial.print("3101");
      DHThumedad(dht2);
      Serial.println("#");
    }
    if(orden == "3FFF"){          // Orden para envío de rango max/min de humedad
      Serial.print("Hum.Maxima: ");
      Serial.print(humMax);
      Serial.print("\tHum.Minima: ");
      Serial.println(humMin);
    }
  }


  // Modo de funcionamiento automático
  /*if(automatico){
    iluminacion();
    climatizacion(dht1, rele3Pin, rele4Pin);
  }*/

  //Comprobación valores máx/min de temperatura
  //sensorDHTmaxmin(dht1);

  // Envío de datos de sensores de forma cíclica
  if (now.minute()>= Tx1){
    /*serialPrintTime(now);
    Serial.print("#");
    Serial.print("2001");             // Sensor DHT nº0 temperatura actual
    DHTtemperatura(dht1);
    Serial.print("#");
    Serial.print("3001");             // Sensor DHT nº0 humedad actual
    DHThumedad(dht1);*/
    /*Serial.print("#");
    Serial.print("2101");             // Sensor DHT nº1 temperatura actual
    DHTtemperatura(dht2);
    Serial.print("#");
    Serial.print("3101");             // Sensor DHT nº1 humedad actual
    DHThumedad(dht2);*/
    
    /*Serial.println("#");
    int i=0;
    EEPROM.get(eeDelay1, i);
    Tx1 = (now.second()+i);*/
    
    /*serialPrintTime(now);
    Serial.print("\tHumidity: ");
    DHThumedad(dht1);
    Serial.print("%\t");
    Serial.print("Temperature: ");
    DHTtemperatura(dht1);
    Serial.println("ºC ");
    Tx1 = (now.minute()+1);
    if(Tx1>=60){
      Tx1=Tx1-60;
    }*/
  }

  orden = "";
}


/*-- Medicion temperatura DHT --*/
void DHTtemperatura(DHT dht){
  float temperatura = dht.readTemperature();
  if(isnan(temperatura)){
    temperatura=-999.99;
  }
  Serial.print(temperatura);
}

/*-- Medicion humedad DHT --*/
void DHThumedad(DHT dht){
  float humedad = dht.readHumidity();
  if(isnan(humedad)){
    humedad=-999.99;
  }
  Serial.print(humedad);
}

/*-- Valores máx/min DHT --*/
/*void sensorDHTmaxmin(DHT dht){
  float humedad = dht.readHumidity();
  float temperatura = dht.readTemperature();
  if((isnan(humedad) || isnan(temperatura))) {
    Serial.println("Failed to read from DHT sensor!");
  }
  else {
    if(temperatura > tempMax){
      tempMax=temperatura;
    }
    if(temperatura < tempMin){
      tempMin=temperatura;
    }
    if(humedad > humMax){
      humMax=humedad;
    }
    if(humedad < humMin){
      humMin=humedad;
    }
  }
}*/

/*-- Guardar dato en EEPROM --*/
void eepromWrite(){
  
}

/*-- Medición generica DHT --*/
/*void sensorDHT(DHT dht){
  float humedad = dht.readHumidity();
  float temperatura = dht.readTemperature();
  if((isnan(humedad) || isnan(temperatura))) {
    Serial.println("Failed to read from DHT sensor!");
  }
  else {
    Serial.print("Humedad: ");
    Serial.print(humedad);
    Serial.print("%\t");
    Serial.print("Temperatura: ");
    Serial.print(temperatura);
    Serial.println("ºC");
  }
}*/

/*-- Función para control de iluminación --*/
void iluminacion(){
  //int movement = digitalRead(PIRPin);
  DateTime now = rtc.now();
  pirState = digitalRead(PIRPin);
  if (pirState == HIGH){                    // Si está activado
    if(ldrState < 50){
      temp = (now.second()+10);
      if (temp >= 60){
        temp=(temp-60);
      }
      digitalWrite(rele1Pin, LOW);          // Rele nº1 ON
    }
    else if (ldrState >=50){
      digitalWrite(rele1Pin, HIGH);         // Rele nº1 OFF
    }
  } 
  else{                          
    digitalWrite(rele1Pin, HIGH);           // Rele nº1 OFF
  }
}

/*-- Función para control de climatización --*/
void climatizacion(DHT dht, int rele1, int rele2){
  float temperatura = dht.readTemperature();
  float humedad = dht.readHumidity();

  if (temperatura < 18){
    digitalWrite(rele1, LOW);
    digitalWrite(rele2, HIGH);
  }
  else if (temperatura > 26 || humedad > 40){
    digitalWrite(rele1, HIGH);
    digitalWrite(rele2, LOW);
  }
  else{
    digitalWrite(rele1, HIGH);
    digitalWrite(rele2, HIGH);
  }
}

/*-- Función para imprimir por puerto serie la fecha --*/
/*void serialPrintDate(DateTime date){
  Serial.print(date.day(), DEC);
  Serial.print("/");
  Serial.print(date.month(), DEC);
  Serial.print("/");
  Serial.print(date.year(), DEC);
}*/

/*-- Función para imprimir por puerto serie la hora --*/
void serialPrintTime(DateTime date){
  Serial.print(date.hour(), DEC);
  Serial.print(":");
  Serial.print(date.minute(), DEC);
  //Serial.print(":");
  //Serial.print(date.second(), DEC);
}

