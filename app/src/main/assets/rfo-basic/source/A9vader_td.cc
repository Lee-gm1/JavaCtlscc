 % console.title " invader 🔅 220425 🔆   "
 % console.title " invader 🔅 220507S 🔆   "
 % console.title " 9vader 🔅 220513T 🔆   "
 %  for demo app to iTc compile...
 % console.title " goovader 🔅 220625s 🔆   "

! Invader.bas    % 1st ver. 220425
! InvaderG.bas   % Hi res. G3 ...
! InvaderS.bas   % Hi speed.
! 9vaderT.cc   % Shoot point. 220513T
! goovaderT5_Ea.cc  % Earth, stats 220625Sa10A.
! goovader.cc % each star, each earth~
! def. x, y (G3, n7, v2, s8), good circle...
! for demo app to iTc compile...
! 20220626Sun, by Oneday group & Team...

xx= 300
yy= 300

dr = 0 % move. dire.

tg = 300 %% Gun.
mm = 0    % 1 : shoot.
gmpnt = 300 % point 100 ++
gmmax = 300 % max point.


   File.exists fex, "Back.mp3"
   if fex = 1 then
      Audio.stop
      AUDIO.LOAD aft, "Back.mp3"
   else
      Audio.stop
      AUDIO.LOAD aft, "9vBack221218Su3.mp3"
   endif
      AUDIO.PLAY aft
      Audio.volume 0.9, 0.9
      Audio.loop
      justFlag = 0

ReStart:

B= 255 % max.
GR.OPEN 255, 10, 10, 10, 1, -1 % dim.

al= 2 % center.

GR.SCREEN w,h % screen w, screen h.
! 2392, 1440
if w > h then
   didsp$ = "H"
else
   didsp$ = "V"
endif

GR.TEXT.ALIGN al % 1 left, 2 center.

      if didsp$ = "H" then
GR.BITMAP.LOAD BM_ptr1H, "eStar_1h.jpg"  %% "Earth_3h.jpg"
       else
GR.BITMAP.LOAD BM_ptr1V, "eStar_1v.jpg"   %% "Earth_3v.jpg"
       endif
GR.BITMAP.LOAD BM_ptr1E, "Earth_1ST.png"  %% "Earth_1st.png"

Alpa = w/h
       if didsp$ = "H" then
Gr.bitmap.scale nbm_ptr1E, bm_ptr1E, 500+(500*Alpa/14), 500, 1 %

       else
Gr.bitmap.scale nbm_ptr1E, bm_ptr1E, 500+(500*Alpa/5), 500, 1 % 
       endif

%%########## 2392, 1440
%% 2392÷1440=  1.661 •• 2131÷1080=  1.973

  GR.BITMAP.LOAD BM_ptr, "I_20220425_M1.png"
%Gr.bitmap.scale nbm_ptr, bm_ptr, 200, 200, 1 % 200, 200
Gr.bitmap.scale nbm_ptr, bm_ptr, 150, 140, 1 % 200, 200

        if didsp$ = "V" then
   Gr.bitmap.scale nbm_ptr1A, bm_ptr1V, w, h-120, 1 %
        else
   Gr.bitmap.scale nbm_ptr1A, bm_ptr1H, w, h, 1 %
        endif
Vstep = h/12  %% 5~ 6 turn.
%% 20220625Sa1103A,  iian.


    ! Back.mp3
    ! 9vBack221218Su3.mp3
    ! 9vHit221218Su3W.mp3

      
ST1:   %%%%%%%%%%%

 if justFlag = 1 then
    justFlag = 0

    Audio.stop       %% Audio.release aft
    
    ! Back.mp3
    ! 9vBack221218Su3.mp3
    ! 9vHit221218Su3W.mp3

   File.exists fex, "Back.mp3"
   if fex = 1 then
      AUDIO.LOAD aft, "Back.mp3"
   else
      AUDIO.LOAD aft, "9vBack221218Su3.mp3"
   endif
      AUDIO.PLAY aft
      Audio.volume 0.9, 0.9
      Audio.loop
 endif

GR.SCREEN w,h % secreen size.
! 2392, 1440
  if w > h then
px = w/3  : py = 450
mx = w- w/3 : my = 450
    if didsp$ <> "H" then
       didsp$ = "H"
       Schg = 1

    endif
  else
px = w/3  : py = 450
mx = w- w/3 : my = 450
    if didsp$ <> "V" then
       didsp$ = "V"
       Schg = 1

    endif
  endif

  if al= 2 then x = w/2 % center.
  if al= 1 then x = 100 % left.
  if al= 3 then x = w -100 % right.

 GR.COLOR B, 100, 150, 255, 1 % blue.

GR.TEXT.BOLD 1

  ! y = 350 % vp. g3A60
    y = 250 % vp. n7A10
  Cz = 80 % 100 % size.
  GR.TEXT.SIZE Cz

     if Schg = 1 then
        Schg = 0
       gr.close
       goto ReStart
     endif

 Gr.bitmap.draw obj_ptr, nbm_ptr1a, 10, 100 % xx, yy %
 

    GR.TEXT.DRAW P, x, y,"goovader 🔅 220625s 🔆   "

          if tch = 0 then
   GR.COLOR 150, 100, 150, 255, 1 % blue.
   if didsp$ = "H" then
   GR.TEXT.SIZE 60 %% 50
   Gr.text.draw P, 500, h-310,"Long touch to screen."
   Gr.text.draw P, 500, h-230,"Shooting & move."
   
   GR.TEXT.SIZE 50 %% 40
   Gr.text.draw P, w-500, h-330,"Demo app for iTc compile."
   Gr.text.draw P, w-500, h-280,"Oneday group & Team."
   Gr.text.draw P, w-500, h-230,"2022.6.25.Sat.10.Am."
   else
   GR.TEXT.SIZE 60 %% 50
   Gr.text.draw P, 280, h-130,"Long touch to screen."
   Gr.text.draw P, 280, h- 50,"Shooting & move."
   
   GR.TEXT.SIZE 50 %% 40
   Gr.text.draw P, w-250, h-140,"Demo app for iTc compile."
   Gr.text.draw P, w-250, h- 90,"Oneday group & Team."
   Gr.text.draw P, w-250, h- 40,"2022.6.25.Sat.10.Am."
   endif
            endif

   GR.COLOR B, 100, 150, 255, 1 % blue.
   GR.TEXT.SIZE Cz % 80
       ll1= len(str$(Dcnt))
       ll2= len(str$(Tcnt))
       aDcnt$ = left$(str$(Dcnt),ll-4) + "00"

      if didsp$ = "H" then
  GR.TEXT.DRAW P, mx+300, 250, aDcnt$
  GR.TEXT.DRAW P, 400, 250, left$(str$(Tcnt),ll-2)
       else
  GR.TEXT.DRAW P, mx+120, 550, aDcnt$
  GR.TEXT.DRAW P, 150, 550, left$(str$(Tcnt),ll-2)
       endif


  Cz = 160 % 100 % size.
  GR.TEXT.SIZE Cz
GR.COLOR B, 250, 250, 100, 1 % yellow.
gmp$ = str$(gmpnt) : gmm$ = str$(gmmax)
GR.TEXT.DRAW P, px, py, left$(gmp$, len(gmp$)-2)

GR.COLOR B, 255, 50, 50, 1 % red.
GR.TEXT.DRAW P, mx, my, left$(gmm$, len(gmm$)-2)


    if mm <> 1 then % no shoot.
Gr.bitmap.draw obj_ptr, nbm_ptr, xx, yy % 300, 300 

    else
        
xx= 300
yy= 300
gmpnt = gmpnt + 100
    if gmpnt > gmmax then
       gmmax = gmpnt
    endif

Gr.bitmap.draw obj_ptr, nbm_ptr, xx, yy % 300, 300 

    endif


    GR.TOUCH touched, tx, ty
    if touched = 1 then
      tt = (h - tg)
  %%  GR.TEXT.DRAW P, tx, ty,"💥"
  %%  GR.TEXT.DRAW P, tx, (h - tg),"💥"

      GR.TEXT.DRAW P, tx, tt,"💥"

      tg = tg + 100 %% 200 %% 100
      if tg > (h - 100)
         tg = 0 % 300
      endif
    else
      tg = 0 % 300
    endif

      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
 
     else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
 
     endif
%%#########


       if (xx + 0) < tx & (xx + 200) > tx then
        %% +100, 170 to, 180 only 10 plus, very easy~ @@ @@   
           if (yy + 30) < tt & (yy + 300) > tt then %% +70,150 adjust. 
Gr.bitmap.scale nbm_ptr, bm_ptr, 50, 40, 1 % 200, 200
           else
Gr.bitmap.scale nbm_ptr, bm_ptr, 150, 140, 1 % 200, 200
           endif
       else
Gr.bitmap.scale nbm_ptr, bm_ptr, 150, 140, 1 % 200, 200
       endif


     %% if (xx - 0) < tx & (xx + 70) > tx then %% 200 very easy.
     %%    if (yy - 0) < tt & (yy + 70) > tt then  
        if (xx + 100) < tx & (xx + 160) > tx then %% +100,170 sume.
        %% +100, 170 to, 180 only 10 plus, very easy~ @@ @@   
        %% +100 & 160, +70 & + 150 : may be just~ ?? 220513Fr0808P
           if (yy + 70) < tt & (yy + 150) > tt then %% +70,150 adjust. 

                mm = 1
                tg = 0 % 300
                tt = (h - tg)

Gr.bitmap.scale nbm_ptr, bm_ptr, 150, 140, 1 % 200, 200

           else
                mm = 0
           endif
        else
           mm= 0
        endif
 

GR.RENDER  % display.



  if mm <> 1 then
    % 400ms
     Sclk = clock()
     Scnt = 0
 
    While ((Cclk - Sclk) < 400)
     SpdC = 1000/3
     Scnt ++   
     Cclk = clock()
    Repeat
    % mx = w- w/3 : my = 450
    Dcnt = Scnt
         % GR.TEXT.DRAW P, mx-500, 600, str$(Dcnt)
    Tcnt ++
  else

    % 5,000ms
 
gr.cls

  Audio.Stop  %% Audio.release aft

    ! Back.mp3
    ! 9vBack221218Su3.mp3
    ! 9vHit221218Su3W.mp3

    AUDIO.LOAD aftw,"9vHit221218Su3W.mp3"
    AUDIO.PLAY aftw
    Audio.volume 0.9, 0.9
    justFlag = 1

    RstFlag = 1 %%%%%%%%
    
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
GR.RENDER
      gosub Pause400
Gr.bitmap.draw obj_ptr, nbm_ptr1a, 10, 100 % xx, yy %
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
%%#########

GR.RENDER
      gosub Pause400

gr.cls

      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
 GR.RENDER
      gosub Pause400

Gr.bitmap.draw obj_ptr, nbm_ptr1a, 10, 100 % xx, yy %
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
%%#########
GR.RENDER
      gosub Pause400

gr.cls
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
GR.RENDER
      gosub Pause400

Gr.bitmap.draw obj_ptr, nbm_ptr1a, 10, 100 % xx, yy %
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
%%#########
    endif

if dr = 0 then
  xx = xx + 80 % 160 % 80 % 50
  if xx > (w -500) then
     dr = 1  % change dire.

     yy = yy + Vstep  %% 100 % 200 % 120 % 70
     if yy > (h - 300) then % 400, 500 %% 1 회 증가 300 O.K!
        yy = 300
        xx = 300
     %   gmpnt = 0 % ending pos.
         gmpnt = gmpnt - 200
         if gmpnt < 0 then
            gmpnt = 0
         endif

         RstFlag = 1  %% ReStart ...

     endif
  endif
else
  xx = xx - 160 % 80 % 50
  if xx < 250 then
     dr = 0  % change dire.

     yy = yy + Vstep %% 200 % 120 % 70
     if yy > (h - 300) then % 400, 500
        yy = 300
        xx = 300
     %  gmpnt = 0 % e.pos.
        gmpnt = gmpnt - 200
         if gmpnt < 0 then
            gmpnt = 0
         endif
         
         RstFlag = 1  %% ReStart ...
         
     endif
  endif
endif

 Gr.cls

%%%%%%%%%%%%%


if RstFlag > 0 then   %%%%%%%%
    RstFlag = 0

    pause 3000

   
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
GR.RENDER
      gosub Pause400
Gr.bitmap.draw obj_ptr, nbm_ptr1a, 10, 100 % xx, yy %
      if didsp$ = "H" then
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540 % xx, yy %
      else
 Gr.bitmap.draw obj_ptr, nbm_ptr1E, w/2-250, h-540-100 % xx, yy %
      endif
%%#########

GR.RENDER

   File.exists fex, "Back.mp3"
   if fex < 1 then
          File.exists fex, "Back_Take.mp3"
      if fex >= 1 then
          File.rename "Back_Take.mp3" , "Back.mp3"
          justFlag = 1
      endif
   endif

    pause 2000

   gr.close

   goto ReStart
endif

%%%%%%%%%%%%%%%

goto ST1 % repeat.


Pause400:
    Sclk = clock()
     Scnt = 0
    While ((Cclk - Sclk) < 400)
     SpdC = 1000/3
     Scnt ++   
     Cclk = clock()
    Repeat
    Dcnt = Scnt
    Tcnt ++
    return

OnGrTouch:
 if tch = 0 then
    tch = 1
 endif
Gr.onGrTouch.resume

onBackKey:
btm2 = clock()
 if (btm2 - btm1) < 2000 then
    end "BackKey End"
    %% Exit
 else
    Popup " Exit   ◀◀"
 endif

btm1 = clock()
Back.resume

bcnt ++
if bcnt >= 2 then
   end "BackKey End"
endif
Back.Resume


%% OnError:

justFlag = 0
gr.close
goto ReStart

OnError.Resume
End 





