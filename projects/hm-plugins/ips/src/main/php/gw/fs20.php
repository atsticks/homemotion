<?php
   $id = (int)$_GET['id'];
   $command = $_GET['cmd'];
   
   $result = false;

   if($command == "switchOn"){
      $result = FS20_SwitchMode($id, true);
   }
   else if($command == "switchOff"){
      $result = FS20_SwitchMode($id, false);
   }
   else if($command == "switchOnFor"){
     $duration = (int)$_GET['duration'];
     $result = FS20_SwitchDuration($id , true, $duration);
   }
   else if($command == "switchOffFor"){
     $duration = (int)$_GET['duration'];
     $result = FS20_SwitchDuration($id , false, $duration);
   }
   else if($command == "dimUp"){
        $result = FS20_DimUp($id);
   }
   else if($command == "dimDown"){
        $result = FS20_DimDown($id);
   }
   else if($command == "enableTimer"){
      $enable = (Boolean)$_GET['enable'];
      $result = FS20_SetEnableTimer($id,$enable);
   }
   else if($command == "enableReceive"){
      $enable = (Boolean)$_GET['enable'];
      $result = FS20_SetEnableReceive($id,$enable);
   }
   else if($command == "setIntensity"){
        $duration = $_GET['duration'];
        $intensity = (int)$_GET['intensity'];
        $result = FS20_SetIntensity($id,$intensity,$duration);
   }
   else if($command == "setHomeCode"){
        $code = $_GET['code'];
        $result = FS20_SetHomeCode($id,$code);
   }
   else if($command == "getHomeCode"){
        print_r(FS20_GetHomeCode($id));
        return;
   }
   else if($command == "setDeviceAddress"){
        $homeCode = $_GET['homecode'];
        $address = $_GET['address'];
        $subAddress = $_GET['subaddress'];
        $result = FS20_SetDeviceAddress($id, $homeCode, $address, $subAddress);
   }
   else if($command == "getDevices"){
        print_r(FS20_GetDevices($id));
        return;
   }
   else if($command == "fhtQueue"){
       print_r(FHZ_GetFHTQueue($id));   
       return;
   }
   else if($command == "dataQueue"){
       print_r(FHZ_GetDataQueue($id));
       return;
   }
   else {
      print_r("ERROR: Invalid command - ".$cmd);
      return;
   }

   if($result)
      echo "OK";
   else
      echo "ERROR"; 
?>