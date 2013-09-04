<?
   $id = (int)$_GET['id'];
   $command = $_GET['cmd'];

   $result = false;

   if($command == "setBoolean"){
      $value = (Boolean)$_GET['value'];
      $param = $_GET['param'];
      $result = HM_WriteValueBoolean($id, $param, $value);
   }
   else if($command == "setFloat"){
      $value = (Float)$_GET['value'];
      $param = $_GET['param'];
      $result = HM_WriteValueFloat($id, $param, $value);
   }
   else if($command == "setString"){
      $value = $_GET['value'];
      $param = $_GET['param'];
      $result = HM_WriteValueString($id, $param, $value);
   }
   else if($command == "setInt"){
      $value = (Integer)$_GET['value'];
      $param = $_GET['param'];
      $result = HM_WriteValueInteger($id, $param, $value);
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