<?php
   $id = (int)$_GET['id'];
   $command = $_GET['cmd'];
   
   $result = false;

   if($command == "setAddress"){
      $address = $_GET['address'];
      $result = FHT_SetAddress($id, $address);
   }
   else if($command == "setTemparature"){
      $temparature = (Float)$_GET['temparature'];
      $result = FHT_SetTemparature($id, $temparature);
   }
   else if($command == "setMode"){
      $mode = $_GET['mode'];
      if($mode == "auto"){
        $result = FHT_SetMode($id, 0);
      }
      if($mode == "manual"){
        $result = FHT_SetMode($id, 1);
      }
      if($mode == "prog"){
        $result = FHT_SetMode($id, 2);
      }
      else{
        $result = FHT_SetMode($id, 0);
      }
   }
   else if($command == "setDate"){
      $year = (Integer)$_GET['y'];
      $month = (Integer)$_GET['m'];
      $day = (Integer)$_GET['d'];
      $result = FHT_SetYear($id, $year);
      $result = FHT_SetMonth($id, $year);
      $result = FHT_SetDay($id, $day);
   }
   else if($command == "setTime"){
      $hour = (Integer)$_GET['h'];
      $minute = (Integer)$_GET['m'];
      $result = FHT_SetHour($id, $hour);
      $result = FHT_SetMinute($id, $minute);
   } 
   else if($command == "requestData"){
      $result = FHT_ReuqestData($id);
   }
   else if($command == "getAddress"){
      print_r(FHT_GetAddress($id));
      return;
   }
   else{
      print_r("ERROR: Invalid command - ".$cmd);
      return;
   }


   if($result)
      echo "OK";
   else
      echo "ERROR"; 
?>