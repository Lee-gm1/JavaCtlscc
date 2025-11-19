console.title "Ascii Hexa Dump • 220820Sat."

fname$ = "../source/GraphicTest1L.cc"

? "File = " + fname$

File.size tbyt, fname$
? "Size = " + int$(tbyt) + " : " + Hex$(tbyt)+ "H bytes" 
?

nn = 1 %% 0
D1$= ""
nt1 = 0
nt2 = 0
nt3 = 0
sta= 0 %% Start Pos.

Byte.open r, bft, fname$
Byte.read.buffer bft, tbyt, buf$

Rp2L:

? "["+ int$(sta+ nn) + " - " + int$(sta+ nn+63) + ~
  " : "+ Hex$(sta+ nn) + "H - " + Hex$(sta+ nn+63) + "H]"

Rp1L:
Dc$ = mid$(buf$,sta+nn,1)

a1$ = upper$(hex$(Ascii(Dc$)))
a2$ = a1$
if len(a1$) = 1 then
   a1$ = "0"+ a1$
endif

if len(a1$) = 3 then  %% ++
   a1$ = right$(a1$,2)
endif

if Dc$ < " " then
   Dc$ = "•"
   if a2$ = "A" then
     Dc$ = "*"
   endif
endif
D1$ = D1$+ a1$+" "
D2$ = D2$+ Dc$+" "
nn++
nt1++
nt3++

if nt1 < 8 then goto Rp1L

? D1$ + "  "+ D2$ %% + "\n"
  nt2++
  D1$ = ""
  D2$ = ""
  nt1 = 0

if nt2 < 8 then goto Rp1L

  D1$ = ""
  D2$ = ""
  nt1 = 0
  nt2 = 0

 if nt3 < tbyt then

? "["+ int$(sta+ nn) + " - " + int$(sta+ nn+63) + ~
  " : "+ Hex$(sta+ nn) + "H - " + Hex$(sta+ nn+63) + "H]"

    goto Rp1L
  endif

?
end "O.K."

! Final Ed. Lin-linear.
! (No Limit Liberty Software) NLLS.
! 20220819Fr1018P, 20220820Sa0139A, Fixed.
