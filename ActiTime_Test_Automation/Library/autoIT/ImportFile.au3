$strFile=$CmdLine[1];
Sleep(2000);
if(WinExists("Open")) Then
   ControlSetText("Open","","Edit1",$strFile);
   Sleep(1000);
   ControlClick("Open","&Open","Button1");
Else
   MsgBox(32,"Error MSg","Failed to find the browse window",10);
EndIf