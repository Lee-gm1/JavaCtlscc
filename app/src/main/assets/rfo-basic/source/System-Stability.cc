

  console.title " System Stability 🔅 220423Sp 🔆"

  Dim Avr[100] % Meas 100T Avr.
Avc = 0
Avt = 0

 B=255 % Brit. max.


GR.OPEN 255, 10, 10, 10, 1, -1 % Dark Scr.

j = 1  % Count var.
ju = 1 % Conti 3600 ?? Cnt.

al= 2 % Char Center.

GR.TEXT.ALIGN al % 1 Char. Left., 2 Center.

ScrDsp = 1

ST1:

GR.SCREEN w,h % Scr Width w, High h : Size in.
! [ 2392, 1440 (L 3~9) :: 2180x1080 (M 7~11) ]

  if al= 2 then x = w/2 % Char. Center.
  if al= 1 then x = w/20 %% 100 % Left.
  if al= 3 then x = w -w/20 %% 100 % Right.

 GR.COLOR B, 100, 150, 255, 1 % Blue.

GR.TEXT.BOLD 1 % Char Bold.

  ! y = 350 % Verti Pos. (2392x1440 : L 3~8)
  ! y = 250 % Verti Pos. (2180x1080 : M 7~11)
    y = h / 4 %% 250 ??

 if w > h then
    Cz0 = h
 else
    Cz0 = w
 endif

  Cz = 70 % 80 % 100 % Char Size.
  Cz = Cz0 / 18

  GR.TEXT.SIZE Cz % Char Size.

    GR.TEXT.DRAW P, x, y,"System Stability🔅 220423Sp 🔆"
    % 220423Sat, 220605Sun.
  
    GR.COLOR B, 150, 150, 250, 1 % blue
    
    Bldtime$ = ""

        Cz1 = Cz0 / 25
    GR.TEXT.SIZE Cz1 %% 52   % 46

    GR.TEXT.DRAW P, x, y+90, str$(Stmax)+"  •  " + "+"+ str$(Stmin)


  if j < 10 then
   GR.COLOR B, 255, 255, 0, 1 % Yel.
  else if j < 20 then
   GR.COLOR B, 255, 0, 0, 1 % Red.
  else if j < 30 then
   GR.COLOR B, 0, 255, 0, 1 % Grn.
  else if j < 40 then
   GR.COLOR B, 255, 0, 255, 1 % Vol.
  else if j < 50 then
   GR.COLOR B, 0, 255, 255, 1 % Cyan.
  else if j < 60 then
   GR.COLOR B, 150, 0, 250, 1 % Dark Vol 1.
  else if j < 70 then
   GR.COLOR B, 80, 250, 100, 1 % ??.
  else if j < 80 then
   GR.COLOR B, 200, 50, 100, 1 % ??.
  else if j < 90 then
   GR.COLOR B, 50, 50, 255, 1 % ??.
  else if j <= 100
   GR.COLOR B, 250, 0, 150, 1 % Pink?
  endif


   d$ = left$(str$(ju),len(str$(ju))-2) % ".0" None.

  y= h - h/6 %% 900 %  Verti Pos. (2180x1080 : M)


    Cz2 = Cz0 / 12
    GR.TEXT.SIZE Cz2 %% 100 
GR.TEXT.DRAW P, x, y, d$ % Count Disp.


Cl = clock()

! Cal, Repeat. 0.5~ 1 Sec.

DLY1:

Spd = 0


DLY2:
Scnt = 0


DLY1S:
   
   Dat1 = 100/3
   Dat2 = Dat1 * 3
   Scnt = Scnt + 1
   Spd = Spd + 1

    if Scnt < 10 then goto DLY1S

Cl2 = clock()
Clt = Cl2 - Cl

    if Clt < 500 then goto DLY2

    GR.COLOR B, 0, 255, 0, 1 % Grn.

    Cz3 = 150  %% Cz0 / 22
    GR.TEXT.SIZE Cz3 %% 50

   y= h - h/3 %% 750 %500 % Verti Pos. 2180x1080 : M

if AvgMe = 0 then  %% div 0
   AvgMe = 1
endif


       stb$ = left$(str$(100- AvgMe/spd * 100), 5)

       while len(stb$) < 5
             stb$ = stb$+ "0"
       repeat

       if left$(stb$,1) = "-" then
          stb$= stb$ + " "
       endif

if ju >= 5 then
  if Stmin < val(stb$) then
     Stmin = val(stb$)
  endif
  if Stmax > val(stb$) then
     Stmax = val(stb$)
  endif
endif

       spdf$ = int$(spd) + " • " + stb$ + " %"

    GR.TEXT.DRAW P, x, y, spdf$ % Disp.


! 100T Avr Disp Part.
! Dim Avr[100] % Meas 100T Avr.
! Avc = 0
! Avt = 0

       Avc ++  
       if (Avc > 100) then Avc = 1 % 100ea Avr.
 Avr [Avc] = spd  % Sto Curr Meas.

! Avr Value 100T Cal.
Avt = 1
Mttl = 0
Tc = 0

CalAvg:

   if Avr[Avt] > 0 then
      Mttl = Mttl + Avr [Avt]
      Tc ++
   endif
   Avt ++

   if Avt <= 100 then goto CalAvg

AvgMe = int(Mttl/Tc)

GR.COLOR B, 255, 255, 0, 1 % Yel.
    Avgsp$ = str$(AvgMe)

    Cz4 = Cz0 / 6
    GR.TEXT.SIZE Cz4 %% 200

     y = h - h/2 %% 600 %%%%%%%%%

    GR.TEXT.DRAW P, x, y, left$(Avgsp$,len(Avgsp$)-2) % Disp.



GR.RENDER  % Disp Real.

  pause 500 % Delay Time.


 Gr.cls % Scr Clear.

j++ % Count Add.
ju++

if ju > 86400 Then ju = 1 % 43200, 86400, 7200, 3600, 200 ... Cont Up Disp.

if j > 100 then j = 1 % Count 100, 10 unit, Modi Color, Disp.

goto ST1 % Repeat Disp.


OnGrTouch:
if ScrDsp = 1 then
   ScrDsp = 0
else
   ScrDsp = 1
endif
Gr.onGrTouch.resume





! System Stabiliy App.
! No Limit Liberty Software: NLLS
! Final ed. 220423Sat,+240607Fri. Lin-linear.



End


! 이 앱의 모국어(한글) 표현(생각, 설계, 구상, 작동, 설명?, 참조)
!
! 목표는(작동, 제어, 행동),
! 폰의 작동이 얼마나 빠르고(연산 속도),
! 얼마나 안정적으로, 작동을 반복 하나? 를, 알기 위해서, 
!
! 간단한 연산을 (100/3 x 3 과, 숫자 +1 을 반복)
!   Dat1 = 100/3
!   Dat2 = Dat1 * 3
!   Scnt = Scnt + 1
!   Spd = Spd + 1
!
! 0.5 초간 실행 해서, 그 반복 횟수를, 1초 마다,
! 보기 편한 적당한 범위 숫자로, 화면에 보기 편하게,
! 속도와 안정도로 표시 하는 목적의 생각(설계) 임.
!
! 작동 안정도는? 그 반복 횟수의 차이 이므로,
! 1초 마다 무한 반복 하는, 횟수를,
! 그동안의 100회 평균 횟수와 비교 해서,
! 그 비율을 안정도로 표시 하는 것 임.
! 
! 실험 결과, 예상 외로, 매초, 매번 반복 측정 결과가,
! 너무나 차이가 크게 나오고, 변동 폭도 심해서,
! 도저히 알아 보기가 곤란 불편할 정도로 심해서,
!
! 그 결과를 100 회, 평균해서, 화면에 표시 하도록,
! 생각을(설계를) 수정, 보완해서, 변동 표시 폭을,
! 100 분의 1 로, 적게, 알아 보기 쉽게 표시 되도록,
! 보완, 변경한 것 임.
! 이것이 생각(목적, 실행, 설계)의 전부고,
!
! 위의 폰 언어의 다른 잡다한 많은 내용(문장, 표현)들은?
! 단지, 그 생각(매초 연산 비교) 내용을, 폰 화면에,
! 좀 보기 좋고, 알아 보기 편하게, 표시 하기 위한,
!
! 목적(작동, 설계) 자체 와는 별개의(? 별 관련 없는?)
! 단지, 화면에 보기 좋은 표시를 하기 위한 부분들일 뿐 임.
!
! 그런데, 위의 전체 표현을(? 내용, 코드, 프로그램?) 보면?
! 그런 부차적인, 그 보기 좋은 화면 표시(제어) 관한 표현이,
! 목적, 목표인 속도 안정도 측정 반복 부분(표현, 코드) 보다,
! 훨씬 복잡하고 코드가(표현, 문장이) 많은 상태 임.
!
! 왜? 단지, 좀 보기 좋은 화면 표시 부분이?
! 복잡 스럽고, 코드가 많은 지는??
! 그것은? 이, 폰 언어가 너무 로우 레벨(낮은 수준?) 이어서,
! 그렇지 않을지?
!
! 즉, 이 보다 더, 하이레벨(높은 수준?) 폰 언어가 있다면?
! 그런 단지 보기 좋은, 기능, 기본적 화면 표시가 아주 간단,
! 몇줄 안되게 될수도 있는 것 임.
!
! 그런데, 유감스럽게도, 현재 이보다 더 하이레벨 폰 언어 조차,
! 아예 없기 때문에, 그런 점에서, 지금의 폰 언어 사용(표현, 번역)이,
! 그 로우레벨, 잡다한 표현 문제로, 상당히 곤란, 쉽지 않은 상태 임.
! 20251029We0208P. 모국어 표현(코드? 설계, 내용) 추가.
!

