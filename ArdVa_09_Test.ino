/*
 * Programa ArdVa v0.9 para control domótico de una casa:
 *    - Uso de RTC
 *    - Uso de DHT11 (preparado para 2 sensores)
 *    - Uso de sensor ultrasonidos (solo un sensor)
 *    - Uso de servo (función creada, sin funcionalidad aún)
 *    - Uso de sensor LDR (preparado para 2 sensores)
 *    - Uso de display LCD 20x4 caracteres conectado por I2C
 * 
 * Funciones integradas: 
 *    - Control de climatizacion (heater/fan) en dos zonas segun sensores DHT independientes
 *    - Control de iluminacion segun sensor LDR y ultrasonidos (2 relés)
 *    - Envío de temp/hum de 2 sensores por puerto serie (cada 30 segundos y a demanda)
 *    - Envío de consumos de 3 sondas por puerto serie (cada 30 segundos y a demanda)
 *    - Display LCD con info de hora, temp/hum de dos sensores e indicacion de estado luces
 *    
 * Protocolo comunicaciones:
 *      0 Ordenes de control [3 definidas en programa]
 *      1 LDR // nº sensor // 0 // 9 envio de info
 *      2 DHT (temp) // nº sensor // 0 // 9 envio de info
 *      3 DHT (hum) // nº sensor // 0 // 9 envio de info
 *      4 Ultrasonidos // nº sensor // 0 // 9 envio de info
 *      5 Consumo // nº sensor // 0 // 9 envio de infor
 *      9 Reles // nº placa de reles // nº rele // on-off-estado (1-0-9)
 *    
 */

//#include <EEPROM.h>             // Librería para memoria EEPROM
//#include <LiquidCrystal_I2C.h>  // Librería para LCD por I2C
//#include <Servo.h>              // Librería para control de servos
//#include <Wire.h>
//#include "DHT.h"                // Librería para sensores DHT
//#include "EmonLib.h"            // Librería para medición de consumos
//#include "RTClib.h"             // Librería para RTC

/*-- Definición de pines a usar --*/
const int LEDPin = 13;        // Pin 13 conectado a LED
String orden = "";
boolean automatico = true;


///////////
// SETUP //
///////////
void setup() {
  Serial.begin(9600);                 // Inicio de comunicaciones serie
  Serial.print("Starting ArdVa...");  // Envía por puerto serie mensaje de inicio de SETUP
  pinMode(LEDPin, OUTPUT);            // Pin LED definido como salida
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
    Serial.println("Modo MANUAL");
  }
  if(orden== "0001"){             // Si la orden recibida es 0001
    automatico=true;              //  entra en modo automático
    Serial.println("Modo AUTOMATICO");
  }

  // Ordenes para modo manual
  if(!automatico){
    if(orden== "9010"){               // Apaga rele nº1
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9011"){               // Enciende rele nº1
      digitalWrite(LEDPin, LOW);
    }
    if(orden== "9020"){               // Apaga rele nº2
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9021"){               // Enciende rele nº2
      digitalWrite(LEDPin, LOW);
    }
    if(orden== "9030"){               // Apaga rele nº3
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9031"){               // Enciende rele nº3
      digitalWrite(LEDPin, LOW);
    }
    if(orden== "9040"){               // Apaga rele nº4
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9041"){               // Enciende rele nº4
      digitalWrite(LEDPin, LOW);
    }
    if(orden== "9050"){        // Apaga rele nº5
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9051"){        // Enciende rele nº5
      digitalWrite(LEDPin, LOW);
    }
    if(orden== "9060"){        // Apaga rele nº6
      digitalWrite(LEDPin, HIGH);
    }
    if(orden== "9061"){        // Enciende rele nº6
      digitalWrite(LEDPin, LOW);
    }
  }

  orden = "";
}
