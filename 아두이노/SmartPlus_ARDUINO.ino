#include <Servo.h>

int q1=0;
int q2=0;
int q3=0;
int q4=0;
int q5=0;
int q6=0;

int time=0;

 char ch =0;
int arr[6]={0,0};

Servo Switch1;
Servo Switch2;
Servo Switch3;
Servo Switch4;
Servo Switch5;
Servo Switch6;

int sw0=0;
int sw1=0;
int sw2=0;
int sw3=0;
int sw4=0;



int sw1_count=0;
int sw2_count=0;
int sw3_count=0;
int sw4_count=0;
int sw5_count=0;
int sw6_count=0;


int sw1_angle =72;
int sw2_angle =72;
int sw3_angle =72;
int sw4_angle =72;
int sw5_angle =72;
int sw6_angle =72;


int sw1_flag=0;
int sw1_flag1=0;
int sw2_flag=0;
int sw2_flag1=0;
int sw3_flag=0;
int sw3_flag1=0;
int sw4_flag=0;
int sw4_flag1=0;
int sw5_flag=0;
int sw5_flag1=0;
int sw6_flag=0;
int sw6_flag1=0;
int t_flag=0;

int value1=0;
int value2=0;
int value3=0;
int value4=0;

void setup() {
  Serial.begin(9600); 
  
  
  
  pinMode(A0,INPUT);  
  
  pinMode(2,INPUT);
  pinMode(4,INPUT);
  pinMode(7,INPUT);
  pinMode(8,INPUT);
  pinMode(12,INPUT);
  
  pinMode(A4,OUTPUT);
  pinMode(A5,OUTPUT);
  pinMode(A6,OUTPUT);
  

 
  digitalWrite(A4,HIGH);
   digitalWrite(A5,LOW);
    digitalWrite(A6,HIGH);
  
  pinMode(3,OUTPUT);
  pinMode(5,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(9,OUTPUT);
  pinMode(10,OUTPUT);
  
  
  Switch1.attach(3,500,2400);
  Switch2.attach(5,500,2400);
  Switch3.attach(6,500,2400);
  Switch4.attach(9,500,2400);
  Switch5.attach(10,500,2400);


 Switch1.write(sw1_angle);
 Switch2.write(sw2_angle);
 Switch3.write(sw3_angle);
 Switch4.write(sw4_angle);
 Switch5.write(sw5_angle);
time=0;
}

void loop() {
 

  
 Switch1.write(sw1_angle);
 Switch2.write(sw2_angle);
 Switch3.write(sw3_angle);
 Switch4.write(sw4_angle);
 Switch5.write(sw5_angle);



 if (Serial.available())
  {
        ch = Serial.read();
           Serial.print(ch); 
       if(ch==33)
       {
        arr[0]++;
        sw0= arr[0]+1;
       }
       else if(ch==38)
       {
        arr[1]++;
       sw1= arr[1]+1; 
       }
       else if(ch==35)
       {
        arr[2]++;
        sw2=arr[2]+1; 
       }
       else if(ch==36)
       {
        arr[3]++;
        sw3= arr[3]+1;
       }
       else if(ch==37)
       {
        arr[4]++;
        sw4= arr[4]+1;
       }
         else if(ch==39)
       {
        arr[5]++;
       }
    
       Serial.flush();
        Serial.print('\n'); 
  }

  if(arr[5]>0)
  {
   time++;
   if(time%1600==0)
   {
      Serial.print(time); 
        Serial.print('\n'); 
      if(t_flag==0)
      {
        Serial.print(time); 
        Serial.print('\n'); 
        digitalWrite(A4,LOW); 
       t_flag=1; 
      }
      else
      {
        Serial.print(time); 
        Serial.print('\n'); 
       digitalWrite(A4,HIGH); 
       t_flag=0; 
      }
   }

 
   if(time>12800)
   {
     t_flag=0;!
     arr[5]--;
   }
  }
  else
  {
   digitalWrite(A4,HIGH); 
   time=0;
  }
 
  if((digitalRead(2)==HIGH&&sw1_flag1==0)||(arr[0]>0))
  {
   
    sw0--;
   sw1_flag1=1; 
  }
  if(sw1_flag1==1)
  {
    q1++;
    if(sw1_flag==0)
    {
       q1=0;
     sw1_flag=1;
    }
    if(sw1_flag==1)
    { 
         sw1_count++;
         q1=0;
    if(sw1_angle>=125&&sw1_count==2000)
    {
         sw1_angle-=60;
         sw1_flag1=0; 
         sw1_count=0;
         sw1_flag=0;
         arr[0]--;
    }
    else if(sw1_angle<=75&&sw1_count==1000)
    {
         sw1_angle+=60;
    }
    }
  }
  
    if((digitalRead(4)==HIGH&&sw2_flag1==0)||(arr[1]>0&&sw1>arr[1]))
  {
   sw2_flag1=1; 
   sw1--;
  }
  if(sw2_flag1==1)
  {
    q2++;
    if(sw2_flag==0)
    {
  
     q2=0;
     sw2_flag=1;
    }
    if(sw2_flag==1)
    { 
         sw2_count++;
         q2=0;
    if(sw2_angle>=117&&sw2_count==2000)
    {
         sw2_angle-=45;
         sw2_flag1=0; 
         sw2_count=0;
         sw2_flag=0;
          arr[1]--;
    }
    else if(sw2_angle<=72&&sw2_count==1000)
    {
         sw2_angle+=45;
            
    }
    }
   
  }
  
  
    if((digitalRead(7)==HIGH&&sw3_flag1==0)||arr[2]>0&&sw2>arr[2])
  {
    sw2--;
   sw3_flag1=1; 
  }
  if(sw3_flag1==1)
  {
    q3++;
    if(sw3_flag==0)
    {
   
     q3=0;
     sw3_flag=1;
    }
    if(sw3_flag==1)
    { 
         sw3_count++;
         q3=0;
    if(sw3_angle>=117&&sw3_count==2000)
    {
         sw3_angle-=45;
         sw3_flag1=0; 
         sw3_count=0;
         sw3_flag=0;
             arr[2]--;
    }
    else if(sw3_angle<=72&&sw3_count==1000)
    {
         sw3_angle+=45;
        
    }
    }
   
  }
  
  
    if((digitalRead(8)==HIGH&&sw4_flag1==0)||arr[3]>0&&sw3>arr[3])
  {
    sw3--;
   sw4_flag1=1; 
  }
  if(sw4_flag1==1)
  {
    q4++;
    if(sw4_flag==0)
    {
     
     q4=0;
     sw4_flag=1;
    }
    if(sw4_flag==1)
    { 
         sw4_count++;
         q4=0;
    if(sw4_angle>=117&&sw4_count==2000)
    {
         sw4_angle-=45;
         sw4_flag1=0; 
         sw4_count=0;
         sw4_flag=0;
          arr[3]--;  
    }
    else if(sw4_angle<=72&&sw4_count==1000)
    {
         sw4_angle+=45;
            Serial.print('b'); 
    }
    }
   
  }
  
    if((digitalRead(12)==HIGH&&sw5_flag1==0)||arr[4]>0&&sw4>arr[4])
  {
    sw4--;
   sw5_flag1=1; 
  }
  if(sw5_flag1==1)
  {
    q5++;
    if(sw5_flag==0)
    {
    
     q5=0;
     sw5_flag=1;
    }
    if(sw5_flag==1)
    { 
         sw5_count++;
         q5=0;
    if(sw5_angle>=117&&sw5_count==2000)
    {
         sw5_angle-=45;
         sw5_flag1=0; 
         sw5_count=0;
         sw5_flag=0;
           arr[4]--;
    }
    else if(sw5_angle<=72&&sw5_count==1000)
    {
         sw5_angle+=45;
    }
    }
   
  }
 ch='b';
  
  
}
