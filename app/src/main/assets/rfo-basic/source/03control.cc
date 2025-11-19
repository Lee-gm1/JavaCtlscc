! 2- Graphic Moving Control, 20240222Th0141P, iian.
! 20240227Tu0833A, SubRtn ++, iian.
! 20240311Mo0106P, full Scr mode, V.H. ++, iian.
!
!
! Gr.open {{alpha}{, red}{, green}{, blue}{, <ShowStatusBar_lexp>}{, <Orientation_nexp>}}
! Gr.screen width, height{, density }
! Gr.color {{alpha}{, red}{, green}{, blue}{, style}{, paint}}
! Gr.screen.to_bitmap <bm_ptr_nvar>
! Gr.bitmap.create <bitmap_ptr_nvar>, width, height
! Gr.text.draw <object_number_nvar>, <x_nexp>, <y_nexp>, <text_object_sexp>
! Gr.text.align {{<type_nexp>}{,<paint_nexp>}}
! Gr.text.size {{<size_nexp>}{,<paint_nexp>}}
!
! Gr.rect <obj_nvar>, left, top, right, bottom
!
! Gr.rotate.start angle, x, y{,<obj_nvar>}
!
! Gr.move <object_ptr_nexp>{{, dx}{, dy}}
!
! Gr.touch touched, x, y
!
!


GR.OPEN 255, 10, 10, 10, 1, -1 % dim.
           % GR.TEXT.ALIGN 1 % 1 % 1 left, 2 center.

Gr.screen w, h
! w,h :: 2177, 1080 :: n11
! w,h :: 2392, 1440 :: g3


Gr.color 255, 255, 255, 0, 1
Gr.text.size 70 % 100 % 80




% Mx = 800
% My = 500
gosub MoveXY

End






!###################### Sub Rtn ######################

MoveXY:

! w,h :: 2177, 1080 :: n11
! w,h :: 2392, 1440 :: g3


vo1lx= w/2   %% 1000  % Yel Box
vo1ty= h-200 %% 1200
vo1rx= vo1lx + 100
vo1by= vo1ty + 100


vo2lx= w/2 + 150  %% 1150  % Red Box
vo2ty= h-200      %% 1200
vo2rx= vo2lx + 100
vo2by= vo2ty + 100

vo3lx= w/2 + 300  %% 850
vo3ty= h- 200     %% 1200
vo3rx= vo3lx + 100
vo3by= vo3ty + 100




Rpt:

%% Gr.screen w, h

%% Rpt1:
gr.cls

Gr.screen w1t, h1t
Gr.screen w, h


! w,h :: 2177, 1080 :: n11
! w,h :: 2392, 1440 :: g3

!! St.
vo1lx= w/2   %% 1000  % Yel Box
vo1ty= h-200 %% 1200
vo1rx= vo1lx + 100
vo1by= vo1ty + 100


vo2lx= w/2 + 150  %% 1150  % Red Box
vo2ty= h-200      %% 1200
vo2rx= vo2lx + 100
vo2by= vo2ty + 100

vo3lx= w/2 + 300  %% 850
vo3ty= h- 200     %% 1200
vo3rx= vo3lx + 100
vo3by= vo3ty + 100
!! Ed.






vlx=200
vty=300
vrx = 300
vby = h-100

Gr.rect o1, vlx, vty , vrx, vby

hlx=200
hty=500
hrx = w-100  % 2100
hby = 580

Gr.rect o1, hlx, hty , hrx, hby
 
%Gr.rect <obj_nvar>, left, top, right, bottom
v2lx=800
v2ty=450
v2rx = 860
v2by = 850 %% 1000

 Gr.rect o2, v2lx, v2ty , v2rx, v2by


Rpt1:
gr.cls

Gr.screen w, h




! w,h :: 2177, 1080 :: n11
! w,h :: 2392, 1440 :: g3


vo1lx= w/2   %% 1000  % Yel Boxw
if w > h then
vo1ty= h-200 %% 1200
else
vo1ty= h-600 %% 1200
endif

vo1rx= vo1lx + 100
vo1by= vo1ty + 100


vo2lx= w/2 + 150  %% 1150  % Red Box
! vo2ty= h-200      %% 1200
if w > h then
vo2ty= h-200 %% 1200
else
vo2ty= h-600 %% 1200
endif

vo2rx= vo2lx + 100
vo2by= vo2ty + 100

vo3lx= w/2 + 300  %% 850
! vo3ty= h- 200     %% 1200

if w > h then
vo3ty= h-200 %% 1200
else
vo3ty= h-600 %% 1200
endif

vo3rx= vo3lx + 100
vo3by= vo3ty + 100







  %% if w <> w1t and h <> h1t then  %% H <-> V ???
  %% if w <> w1t & h <> h1t then  %% H <-> V :: O.K.
  if w <> w1t | h <> h1t then  %% H <-> V :: O.K.
  %% if w <> w1t then  %% H <-> V :: O.K.
      Gr.screen w1t, h1t
     %% Gr.screen w, h
%%///////////////////////////
vlx=200
vty=300
vrx = 300
vby = h-100

Gr.rect o1, vlx, vty , vrx, vby

hlx=200
hty=500
hrx = w-100  % 2100
hby = 580

Gr.rect o1, hlx, hty , hrx, hby
 
%Gr.rect <obj_nvar>, left, top, right, bottom
v2lx=800
v2ty=450
v2rx = 860
v2by = 850 %% 1000

 Gr.rect o2, v2lx, v2ty , v2rx, v2by
%%//////////////////////////////
   endif



Gr.text.size 50 % 70 % 100 % 80
Gr.color 255, 200, 200, 0, 1

%% gr.text.draw P,1000, 200,"• Moving Control •"
%% gr.text.draw P,w/2-400, 200,"• Moving Control • "+int$(w)+" "+int$(h)
gr.text.draw P,w/2-400, 200,"• Moving Control 0.1 • "+int$(w)+" "+int$(h)

Gr.color 255, 100, 100, 0, 1
Gr.rect o1, vlx, vty , vrx, vby

Gr.color 255, 150, 50, 0, 1
Gr.rect o1, hlx, hty , hrx, hby

Gr.color 250, 0, 120, 170, 1
Gr.rect o2, v2lx, v2ty , v2rx, v2by

%%#####################


Gr.color 250, 220, 220, 0, 1
Gr.rect oo1, vo1lx, vo1ty , vo1rx, vo1by


Gr.color 250, 220, 0, 0, 1
Gr.rect oo2, vo2lx, vo2ty , vo2rx, vo2by



Gr.color 250, 0, 255, 0, 1
Gr.rect oo2, vo3lx, vo3ty , vo3rx, vo3by




Gr.color 255, 200, 200, 0, 1

Gr.text.size 100 % 100 % 80

gr.text.draw P,400-200, h-150,"◀"
gr.text.draw P,700-200, h-150,"▶"

gr.text.draw P,550-200, h-250,"🔺"
gr.text.draw P,550-200, h-50,"🔻"

Gr.text.size 120 % 100 % 80
gr.text.draw P,w-400, h-150,"🔗"
gr.text.draw P,w-200, h-150,"💢"



%%############### Move Control #########
Gr.touch touched, xx, yy
 if touched = 1 then


%% if yy > h-150 & yy < h-50 & xx >400-200 & xx <800-200 then
   if yy > h-150 & yy < h-10 & xx >400-200 & xx <800-200 then

%% Gr.rect o3, 400-200, h-150 , 800-200, h-10 % down 

   
  if v2by < (h -200) then
   hty= hty+3  %%   ++  Down
   hby= hby+3  %%   ++
   v2ty=v2ty+3 %%   ++
   v2by=v2by+3 %%   ++
  endif

endif

% if yy > h-350 & yy < h-250 & xx >400-200 & xx <800-200 then
  if yy > h-400 & yy < h-250 & xx >400-200 & xx <800-200 then
 
%% Gr.rect o3, 400-200, h-400 , 800-200, h-250 % up

  if v2ty > 200 then
   hty= hty -3   %%  --  Up
   hby= hby -3   %%  --
   v2ty= v2ty -3 %%  --
   v2by= v2by -3 %%  --
  endif
endif

% if xx > 700-180 & xx < 800-150 & yy > h-350 & yy < h-50 then
  if xx > 700-220 & xx < 800-100 & yy > h-400 & yy < h-10 then

% Gr.rect o3, 700-220, h-400 , 800-100, h-10 % right
  if v2lx < (w-300) then
   v2lx= v2lx+6 % 3  %% ++ Right
   v2rx= v2rx+6 % 3  %% ++
  endif
endif

% if xx < 500-200 & xx > 400-200 & yy > h-350 & yy < h-50 then
  if xx < 500-200 & xx > 400-300 & yy > h-400 & yy < h-10 then

% Gr.rect o3, 400-300, h-400 , 500-200, h-10 % left
  if v2lx > 200 then
   v2lx=v2lx -6 % 3  %% -- Left
   v2rx=v2rx -6 % 3  %% --
  endif
endif
endif  %% touched = 1
%%############## Move Ctrl End #########

 gr.Render

goto Rpt1 %% Rpt



onBackKey:
gr.close

End

%% Exit

return



gr.close

end
