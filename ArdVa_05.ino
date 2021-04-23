/*
 * Programa ArdVa v0.5 para control domótico de una casa:
 *    - Uso de RTC
 *    - Uso de DHT11 (preparado para 2 sensores, solo uno en hardware)
 *    - Uso de sensor PIR (función creada, sin probar en hardware)
 *    - Uso de servo (función creada, sin funcionalidad aún)
 *    - Uso de sensor LDR (preparado para 2 sensores, solo uno en hardware)
 *    - Uso de display LCD 20x4 caracteres conectado por I2C
 * 
 * Funciones integradas: 
 *    - Control de climatizacion (heater/fan) en dos zonas segun sensores DHT independientes
 *    - Control de iluminacion segun sensor PIR (2 relés)
 *    - Envío de temp/hum de 2 sensores por puerto serie (cada 30 segundos y a demanda)
 *    - Envío de consumos de 3 sondas por puerto serie (cada 30 segundos y a demanda)
 *    - Display LCD con info de hora, temp/hum de dos sensores e indicacion de estado luces
 *    
 * Protocolo comunicaciones:
 *      0 Ordenes de control [3 definidas en programa]
 *      1 LDR // nº sensor // 0 // 1 envio de info
 *      2 DHT (temp) // nº sensor // 0 // 1 envio de info
 *      3 DHT (hum) // nº sensor // 0 // 1 envio de info
 *      4 PIR // nº sensor // 0 // 1 envio de info
 *      5 Consumo // nº sensor // 0 // 1 envio de infor
 *      9 Reles // nº placa de reles // nº rele // on-off-estado (1-0-9)
 *    
 */

#include <EEPROM.h>             // Librería para memoria EEPROM
#include <LiquidCrystal_I2C.h>  // Librería para LCD por I2C
#include <Servo.h>              // Librería para control de servos
#include <Wire.h>
#include "DHT.h"                // Librería para sensores DHT
#include "EmonLib.h"            // Librería para medición de consumos
#include "RTClib.h"             // Librería para RTC

/*-- Definición de pines a usar --*/
const int LEDPin = 13;        // Pin 13 conectado a LED
const int PIR1Pin = 22;       // Pin 22 conectado al sensor PIR nº1
const int PIR2Pin = 23;       // Pin 22 conectado al sensor PIR nº2
const int DHT1Pin = 24;       // Pin 20 conectado al sensor DHT nº1
const int DHT2Pin = 25;       // Pin 21 conectado al sensor DHT nº2
const int rele1Pin = 30;      // Pin para activar rele 1
const int rele2Pin = 31;      // Pin para activar rele 2
const int rele3Pin = 32;      // Pin para activar rele 3
const int rele4Pin = 33;      // Pin para activar rele 4
const int rele5Pin = 34;      // Pin para activar rele 5
const int rele6Pin = 35;      // Pin para activar rele 6
const int rele7Pin = 36;      // Pin para activar rele 7
const int rele8Pin = 37;      // Pin para activar rele 8
const int LDR1Pin = A8;       // Pin lectura LDR
const int LDR2Pin = A9;       // Pin lectura LDR

/*-- Listado sensores DHT a usar --*/
#define DHTTYPE DHT11           // Modelo de DHT usado (DHT11, DHT22, DHT21)
DHT dht1(DHT1Pin, DHTTYPE);   // Definicion de sensor DHT nº1 (pin, modelo)
DHT dht2(DHT2Pin, DHTTYPE);   // Definicion de sensor DHT nº2 (pin, modelo)

/*-- Datos para uso LCD --*/
LiquidCrystal_I2C lcd(0x27,20,4);   // set the LCD address to 0x27 for a 16 chars and 2 line display

/*-- Datos para uso RTC --*/
RTC_DS1307 rtc;                     // Modelo de RTC en uso (RTC_DS1307, RTC_DS3231)
char daysOfTheWeek[7][12] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

/*-- Definicion de servos a usar --*/
Servo servo1;

/*-- Datos para uso de sensor de energía --*/
#define FILTERSETTLETIME 5000       //  Time (ms) to allow the filters to settle before sending data
const int CT1 = 1;                  // Seleccionar 1 para activar o 0 para desactivar la sonda
const int CT2 = 1;                  // Seleccionar 1 para activar o 0 para desactivar la sonda
const int CT3 = 1;                  // Seleccionar 1 para activar o 0 para desactivar la sonda
const int CT4 = 0;                  // Seleccionar 1 para activar o 0 para desactivar la sonda
EnergyMonitor ct1,ct2,ct3,ct4;                                                // Create  instances for each CT channel
typedef struct { int power1, power2, power3, power4, nothing;} PayloadTX;     // Create structure - a neat way of packaging data for RF comms, nothing is added a a 5th integer to match data structure of voltage version
PayloadTX emontx;
const int LEDpin = 9;               // On-board emonTx LED
boolean settled = false;


/*-- Listado variables --*/
int PIR1State = LOW;          // De inicio no hay movimiento
int PIR2State = LOW;          // De inicio no hay movimiento
const int threshold = 800;    // Valor de referencia para LDR
String orden = "";            // Guarda el string recibido por puerto serie para comandos
boolean automatico = true;    // Boleano para indicar si está en modo automático o manual
boolean releStatus = false;   // Boleano para estado de ON/OFF de los reles
int Tx1 = 0;                  // Variable usada para definir el tiempo de envío de información hacia Nodo
int Tx2 = 0;                  // Variable usada para definir el tiempo de envío de información hacia Nodo


///////////
// SETUP //
///////////
void setup() {
  Serial.begin(9600);                 // Inicio de comunicaciones serie
  Serial.print("Starting ArdVa...");  // Envía por puerto serie mensaje de inicio de SETUP
  //EEPROM.put(eeDelay1, 10);         // Pone 10s en el temporizador de la EEPROM
  pinMode(LEDPin, OUTPUT);            // Pin LED definido como salida
  pinMode(PIR1Pin, INPUT);            // Pin PIR definido como entrada
  pinMode(PIR2Pin, INPUT);            // Pin PIR definido como entrada
  pinMode(DHT1Pin, INPUT);            // Pin DHT nº1 definido como entrada
  pinMode(DHT2Pin, INPUT);            // Pin DHT nº2 definido como entrada
  pinMode(rele1Pin, OUTPUT);          // Pin de rele nº1 definido como salida
  pinMode(rele2Pin, OUTPUT);          // Pin de rele nº2 definido como salida
  pinMode(rele3Pin, OUTPUT);          // Pin de rele nº3 definido como salida
  pinMode(rele4Pin, OUTPUT);          // Pin de rele nº4 definido como salida
  pinMode(rele5Pin, OUTPUT);          // Pin de rele nº5 definido como salida
  pinMode(rele6Pin, OUTPUT);          // Pin de rele nº6 definido como salida
  pinMode(LDR1Pin, INPUT);            // Pin de LDR nº1 definido como entrada
  pinMode(LDR2Pin, INPUT);            // Pin de LDR nº2 definido como entrada
  dht1.begin();                       // Inicia sensor DHT nº1
  dht2.begin();                       // Inicia sensor DHT nº2
  servo1.attach(10);                  // Inicia servo
  digitalWrite(rele1Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  digitalWrite(rele2Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  digitalWrite(rele3Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  digitalWrite(rele4Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  digitalWrite(rele5Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  digitalWrite(rele6Pin, HIGH);       // Pone los reles en HIGH para desactivar las salidas
  /* Calibración personal de sondas de consumo */
  if (CT1) ct1.current(1, 83);        // Setup emonTX CT channel (channel, calibration)
  if (CT2) ct2.current(2, 83.1);      // Calibration factor = CT ratio / burden resistance
  if (CT3) ct3.current(3, 84); 
  if (CT4) ct4.current(4, 85);
  if (!rtc.begin()){                          // Si no inicia el rtc
    Serial.println("Couldn't find RTC");      // Avisa por puerto serie
  }
  if(!rtc.isrunning()){                               // Si no funciona el rtc
    Serial.println("RTC is NOT running!!");           // Avisa por puerto serie
    rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));   // Pone en hora con datos de la compilación
  }
  delay(1000);                        // Demora dos segundos iniciando para que arranquen los sensores
  lcd.init();                         // Inicia el LCD
  lcd.backlight();                    // Enciende la retroiluminacion del LCD
  Serial.println(" OK\n");            // Imprime mensaje de fin de SETUP por puerto serie
}

//////////
// LOOP //
//////////
void loop() {
  while(Serial.available()){      // Si hay datos de entrada en puerto serie
    char o=Serial.read();         // Lee caracter a caracter
    if(o!='.'){                   // Si son distintos de '.'
      orden+=o;                   // Agregar al string orden
    }
  }
  delay(30);                      // Retardo necesario para que coja todos los caracteres como un string

  // Ordenes de control
  if(orden== "0000"){             // Si la orden recibida es 0000
    automatico=false;             //  entra en modo manual
    //Serial.println("Modo MANUAL");
  }
  if(orden== "0001"){             // Si la orden recibida es 0001
    automatico=true;              //  entra en modo automático
    //Serial.println("Modo AUTOMATICO");
  }
  if(orden== "0010"){             // Esta orden es un volcado de datos de todos los sensores
    Serial.print("2001");         // Sensor DHT nº0 temperatura actual
    DHTtemp(dht1);
    Serial.print("#");
    Serial.print("3001");         // Sensor DHT nº0 humedad actual
    DHThum(dht1);
    Serial.print("#");
    Serial.print("2101");         // Sensor DHT nº1 temperatura actual
    DHTtemp(dht2);
    Serial.print("#");
    Serial.print("3101");         // Sensor DHT nº1 humedad actual
    DHThum(dht2);
    Serial.print("#");
    Serial.print("5001");         // Sonda consumo nº1
    Serial.print(consumo1());
    Serial.print("#");
    Serial.print("5101");         // Sonda consumo nº2
    Serial.print(consumo2());
    Serial.print("#");
    Serial.print("5201");         // Sonda consumo nº3
    Serial.print(consumo3());
    Serial.println("");
  }

  // Ordenes para modo manual
  if(!automatico){
    if(orden== "9010"){               // Apaga rele nº1
      digitalWrite(rele1Pin, HIGH);
    }
    if(orden== "9011"){               // Enciende rele nº1
      digitalWrite(rele1Pin, LOW);
    }
    if(orden== "9020"){               // Apaga rele nº2
      digitalWrite(rele2Pin, HIGH);
    }
    if(orden== "9021"){               // Enciende rele nº2
      digitalWrite(rele2Pin, LOW);
    }
    if(orden== "9030"){               // Apaga rele nº3
      digitalWrite(rele3Pin, HIGH);
    }
    if(orden== "9031"){               // Enciende rele nº3
      digitalWrite(rele3Pin, LOW);
    }
    if(orden== "9040"){               // Apaga rele nº4
      digitalWrite(rele4Pin, HIGH);
    }
    if(orden== "9041"){               // Enciende rele nº4
      digitalWrite(rele4Pin, LOW);
    }
    if(orden== "9050"){        // Apaga rele nº5
      digitalWrite(rele5Pin, HIGH);
    }
    if(orden== "9051"){        // Enciende rele nº5
      digitalWrite(rele5Pin, LOW);
    }
    if(orden== "9060"){        // Apaga rele nº6
      digitalWrite(rele6Pin, HIGH);
    }
    if(orden== "9061"){        // Enciende rele nº6
      digitalWrite(rele6Pin, LOW);
    }
  }
  
  // Ordenes para petición de información de estado de reles / medidas de sensores
  if(orden== "9019"){                       // Información de estado de rele nº1
    if (digitalRead(rele1Pin) == true){     // Si está apagado
      Serial.println("90190#");
    }
    else {                                  // Si está encendido
      Serial.println("90191#");
    }
  }
  if(orden== "9029"){                       // Información de estado de rele nº2
    if (digitalRead(rele2Pin) == true){     // Si está apagado
      Serial.println("90290#");
    }
    else {                                  // Si está encendido
      Serial.println("90291#");
    }
  }
  if(orden== "9039"){                       // Información de estado de rele nº3
    if (digitalRead(rele3Pin) == true){     // Si está apagado
      Serial.println("90390#");
    }
    else {                                  // Si está encendido
      Serial.println("90391#");
    }
  }
  if(orden== "9049"){                       // Información de estado de rele nº4
    if (digitalRead(rele4Pin) == true){     // Si está apagado
      Serial.println("90490#");
    }
    else {                                  // Si está encendido
      Serial.println("90491#");
    }
  }
  if(orden== "9059"){                       // Información de estado de rele nº5
    if (digitalRead(rele5Pin) == true){     // Si está apagado
      Serial.println("90590#");
    }
    else {                                  // Si está encendido
      Serial.println("90591#");
    }
  }
  if(orden== "9069"){                       // Información de estado de rele nº6
    if (digitalRead(rele6Pin) == true){     // Si está apagado
      Serial.println("90690#");
    }
    else {                                  // Si está encendido
      Serial.println("90691#");
    }
  }


  // Modo de funcionamiento automático
  climatizacion(dht1, rele3Pin, rele4Pin);    // Uso del sensor dht1 para control de calentador 1 y ventilador 1
  climatizacion(dht2, rele5Pin, rele6Pin);    // Uso del sensor dht2 para control de calentador 2 y ventilador 2
  LDRsensor(LDR1Pin, rele1Pin);       // Controla el encendido de rele 1 en función de LDR 1
  LDRsensor(LDR2Pin, rele2Pin);       // Controla el encendido de rele 2 en función de LDR 2
  PIRsensor(PIR1Pin);                 // Medición de sensor PIR1
  PIRsensor(PIR2Pin);                 // Medición de sensor PIR1
  servoControl(servo1);               // Control de servo
  menu();                             // Imprime "menu" en LCD

  

  // Envío de datos de sensores de forma cíclica
  /*DateTime now = rtc.now();
  if (now.second()>= Tx1){
    Serial.print("2001");         // Sensor DHT nº0 temperatura actual
    DHTtemp(dht1);
    Serial.print("#");
    Serial.print("3001");         // Sensor DHT nº0 humedad actual
    DHThum(dht1);
    Serial.print("#");
    Serial.print("2101");         // Sensor DHT nº1 temperatura actual
    DHTtemp(dht2);
    Serial.print("#");
    Serial.print("3101");         // Sensor DHT nº1 humedad actual
    DHThum(dht2);
    Serial.print("#");
    Serial.print("5001");         // Sonda consumo nº1
    Serial.print(consumo1());
    Serial.print("#");
    Serial.print("5101");         // Sonda consumo nº2
    Serial.print(consumo2());
    Serial.print("#");
    Serial.print("5201");         // Sonda consumo nº3
    Serial.print(consumo3());
    Serial.println("");
    
    Tx1 = (now.second()+30);
    if(Tx1>=60){
      Tx1=Tx1-60;
    }
  }*/


  // funcion servo
  // funcion funcion PIR
/*      1 LDR // nº sensor // 0 // 1 envio de info
 *      2 DHT (temp) // nº sensor // 0 // 1 envio de info
 *      3 DHT (hum) // nº sensor // 0 // 1 envio de info
 *      4 PIR // nº sensor // 0 // 1 envio de info
 *      5 Consumo // nº sensor // 0 // 1 envio de infor
 *      9 Reles // nº placa de reles // nº rele // on-off-estado (1-0-9)*/



 
 if (orden!=""){
  Serial.println(orden);
 }
  orden = "";
}

/*-- Medicion temperatura DHT --*/
void DHTtemp(DHT dht){
  int temperatura = dht.readTemperature();
  if(isnan(temperatura)){
    temperatura=-999;
  }
  Serial.print(temperatura);
}

/*-- Medicion humedad DHT --*/
void DHThum(DHT dht){
  int humedad = dht.readHumidity();
  if(isnan(humedad)){
    humedad=-999;
  }
  Serial.print(humedad);
}

/*-- Control climatizacion --*/
void climatizacion(DHT dht, int heater, int fan){
  int temperatura = dht.readTemperature();
  int humedad = dht.readHumidity();
  if (temperatura > 28 || humedad > 40){
    digitalWrite(fan, LOW);                 // Enciende ventilador
    digitalWrite(heater, HIGH);             // Apaga calentador
  }
  else if (temperatura < 20) {
    digitalWrite(fan, HIGH);                // Apaga ventilador
    digitalWrite(heater, LOW);              // Enciende calentador
  }
  else {
    digitalWrite(fan, HIGH);                // Apaga ventilador
    digitalWrite(heater, HIGH);             // Apaga calentador
  }
}

/*-- Medicion luz --*/
void LDRsensor(int ldr, int rele){
  int input = analogRead(ldr);
   if (input > threshold) {
      digitalWrite(rele, HIGH);
   }
   else {
      digitalWrite(rele, LOW);
   }
}

/*-- Medicion PIR --*/
bool movement(int pin, bool PIRState){
  int val = digitalRead(pin);
  if (val == HIGH) {
    digitalWrite(LEDPin, HIGH);
    if (PIRState == LOW){             // Si previamente estaba apagado
      PIRState == HIGH;
    }
  }
  else {
    digitalWrite(LEDPin, LOW);
    if (PIRState == HIGH) {
      PIRState == LOW;
    }
  }
  return PIRState;
}

/*-- Medicion movimiento --*/
void PIRsensor(int pir){
  
}

/*-- Mediciones de consumo --*/
double consumo1(){
  if (CT1){
    emontx.power1 = ct1.calcIrms(1480) * 240.0;     //ct.calcIrms(number of wavelengths sample)*AC RMS voltage
    return emontx.power1;
  }
}
double consumo2(){
  if (CT2){
    emontx.power2 = ct2.calcIrms(1480) * 240.0;
    return emontx.power2;
  }
}
double consumo3(){
  if (CT3){
    emontx.power3 = ct3.calcIrms(1480) * 240.0;
    return emontx.power3;
  }
}

/*-- Control servo --*/
void servoControl(Servo servo){
  
}

/*-- Funcion para LCD --*/
void menu(){
  int t1 = dht1.readTemperature();
  int t2 = dht2.readTemperature();
  int h1 = dht1.readHumidity();
  int h2 = dht2.readHumidity();
  
  lcd.setCursor(0,0);
  lcd.print("ArdVa v0.5  ");
  lcdTime();
  lcd.setCursor(0,1);
  lcd.print("T1: ");
  lcd.print(t1);
  lcd.print((char)223);
  lcd.print("C  ");
  lcd.setCursor(10,1);
  lcd.print("H1: ");
  lcd.print(h1);
  lcd.print("%"  );
  lcd.setCursor(0,2);
  lcd.print("T2: ");
  lcd.print(t2);
  lcd.print((char)223);
  lcd.print("C  ");
  lcd.setCursor(10,2);
  lcd.print("H2: ");
  lcd.print(h2);
  lcd.print("%"  );
  lcd.setCursor(0,3);
  lcd.print("L1: ");
  if (digitalRead(rele1Pin) == LOW){
    lcd.print("ON ");
  }
  else { lcd.print("OFF ");}
  lcd.setCursor(10,3);
  lcd.print("L2: ");
  if (digitalRead(rele2Pin) == LOW){
    lcd.print("ON ");
  }
  else { lcd.print("OFF ");}
}

/*-- Hora en lcd --*/
void lcdTime(){
  DateTime now = rtc.now();
  if (now.hour()<10){ lcd.print("0");}
  lcd.print(now.hour());
  lcd.print(":");
  if (now.minute()<10){ lcd.print("0");}
  lcd.print(now.minute());
  lcd.print(":");
  if (now.second()<10){ lcd.print("0");}
  lcd.print(now.second());
}

/*-- Fecha en LCD --*/
void lcdDate(){
  DateTime now = rtc.now();       // Toma valores de fecha/hora actuales
  if (now.day()<10){ lcd.print("0");}
  lcd.print(now.day());
  Serial.print("/");
  Serial.print(now.month(), DEC);
  Serial.print("/");
  Serial.print(now.year(), DEC);
  Serial.print(" -- ");
  Serial.print(now.hour(), DEC);
  Serial.print(":");
  Serial.print(now.minute(), DEC);
  Serial.print(":");
  Serial.println(now.second(), DEC);
}
