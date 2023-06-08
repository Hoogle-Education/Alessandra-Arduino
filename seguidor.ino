#define pinSensorDir 4
#define pinSensorEsq 12
#define dirFrente 6
#define dirTras   5
#define esqFrente 10
#define esqTras   11
#define buzzer  13

#define LINHA HIGH

#define FRENTE  1
#define PARADO  0
#define TRAS   -1

#define teste loop

// -----------------------------
// estados
#define BRANCO 1
#define PRETO 0

int estado_anterior = BRANCO;

// contador para virar a direita
int count = 0;
// -----------------------------

// DECLARAÇÃO DE FUNÇÕES
void configMotor();
void motorEsq(int direcao, byte velocidade = 35);
void motorDir(int direcao, byte velocidade = 35);

// -----------------------------

// DECLARAÇÃO DE VARIÁVEIS
bool leituraEsquerda;
bool leituraDireita;

// -----------------------------

void virar_na_terceira(int estado_atual){
  
  // se troquei de BRANCO -> PRETO
  if(estado_atual != estado_anterior 
      && estado_anterior == BRANCO) {
        cont++;
      } 

  // se contei 3 vezes e estou numa situação de curva
  if(cont == 3 && estado_atual == PRETO) {
    motorEsq(FRENTE, 35);
    motorDir(TRAS, 35);
  } else cont == 0; // reinicio o contador 
  
  // estado atual passa a ser o estado anterior
  estado_anterior = estado_atual;
}

// -----------------------------

void setup() {
  pinMode(pinSensorDir, INPUT);
  pinMode(pinSensorEsq, INPUT);
  pinMode(buzzer, OUTPUT);

  configMotor();
}

void loop( ) {
  bool valE = digitalRead(pinSensorEsq);
  bool valD = digitalRead(pinSensorDir);

  if (valE == LINHA && valD == LINHA) {
    virar_na_terceira(PRETO);
  }
  else if (valD == LINHA) {
    motorEsq(FRENTE, 35);
    motorDir(TRAS, 35);
  }
  else if (valE == LINHA) {
    motorEsq(TRAS, 35);
    motorDir(FRENTE, 35);
  }
  else {
    motorEsq(FRENTE, 35);
    motorDir(FRENTE, 35);
  }

}

// IMPLEMENTO DE FUNÇÕES

void configMotor() {
  pinMode(dirFrente,  OUTPUT);
  pinMode(dirTras,    OUTPUT);
  pinMode(esqFrente,  OUTPUT);
  pinMode(esqTras,    OUTPUT);

  digitalWrite(dirFrente,  LOW);
  digitalWrite(dirTras,    LOW);
  digitalWrite(esqFrente,  LOW);
  digitalWrite(esqTras,    LOW);
}

void motorEsq(int direcao, byte velocidade) {//80****MOTOR DA ESQUERDA*****
  switch (direcao) {
    case -1: {
        //        Serial.println("Esq Trás");
        digitalWrite(esqFrente,  LOW);
        analogWrite (esqTras,    velocidade);
        break;
      }
    case 0: {
        //        Serial.println("Esq PARADOdo");
        digitalWrite(esqFrente,  HIGH);
        digitalWrite(esqTras,    HIGH);
        break;
      }
    case 1: {
        //        Serial.println("Esq Frente");
        analogWrite (esqFrente,  velocidade);
        digitalWrite(esqTras,    LOW);
        break;
      }
  }
}

void motorDir(int direcao, byte velocidade) {//95**MOTOR DA DIREITA*****
  switch (direcao) {
    case -1: {
        //        Serial.println("Dir Trás");
        digitalWrite(dirFrente,  LOW);
        analogWrite (dirTras,    velocidade);
        break;
      }
    case 0: {
        //        Serial.println("Dir PARADOdo");
        digitalWrite(dirFrente,  HIGH);
        digitalWrite(dirTras,    HIGH);
        break;
      }
    case 1: {
        //        Serial.println("Dir Frente");
        analogWrite (dirFrente,  velocidade);
        digitalWrite(dirTras,    LOW);
        break;
      }
  }
}