<?php
   $command = $_GET['cmd'];
   if($command == "getDir"){
   	print_r(IPS_GetKernelDir());
   }
   else if($command == "getVersion"){
      print_r(IPS_GetKernelVersion());
   }
   else if($command == "getStartTime"){
      print_r(gmdate("Y.m.d H:1:s.",IPS_GetUptime()));
   }
   else if($command == "getProcessInfo"){
      print_r(Sys_GetProcessInfo());
   }
   else if($command == "getMemoryInfo"){
      print_r(Sys_GetMemoryInfo());
   }
   else if($command == "getHardDiskInfo"){
      print_r(Sys_GetHardDiskInfo());
   }
   else if($command == "getNetworkInfo"){
      print_r(Sys_GetNetworkInfo());
   }
   else if($command == "getBattery"){
      print_r(Sys_GetBattery());
   }
   else if($command == "getSpooler"){
      print_r(Sys_GetSpooler());
   }
   else{
      print_r("ERROR: Invalid command - ".$cmd);
      return;
   }
?>