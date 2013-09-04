<?php
   $id = (int)$_GET['id'];
   $command = $_GET['cmd'];
   
   $result = false;

   if($command == "setname"){
      $name = $_GET['name'];
      $result = IPS_SetName($id, name);
   }
   else if($command == "getname"){
      print_r(IPS_GetName($id));
      return;
   }
   else if($command == "setinfo"){
      $info = $_GET['info'];
      $result = IPS_SetInfo($id, $info);
   }
   else if($command == "getinfo"){
      print_r(IPS_GetInfo($id));
      return;
   }
   else if($command == "objectexists"){
     if(IPS_ObjectExists($id)){
       print_r("true");
     }
     else{
       print_r("false");
     }
     return;
   }
   else if($command == "getinstvar"){
      $varname = $_GET['name'];
      $varid = IPS_GetVariableIDByName($varname,$id);
      print_r( IPS_GetVariable($varid));
      return;
   }
   else if($command == "getchildren"){
      $varids = IPS_GetChildrenIDs($id);
	foreach($varids as $var){
 		$object = IPS_GetObject($var);
 		$parent = IPS_GetObject($object['ParentID']);
 		$name = $object['ObjectName'];
 		while($parent['ObjectName'] != "IP-Symcon"){
			$parent = IPS_GetObject($object['ParentID']);
			if($parent['ObjectName'] != "IP-Symcon"){
		  		$name = $parent['ObjectName']."\\".$name;
			}
			$object = $parent;
		}
		print_r($var.":".$name."\n");
	}
      return;
   }
   else if($command == "getParent"){
      $object = IPS_GetObject($id);
      print_r($object['ParentID']);
      return;
   }
   else if($command == "getvar"){
      $result = IPS_GetVariable($id);
      print_r($result);
      return;
   }
   else if($command == "getvarevents"){
     print_r(IPS_GetVariableEventList($id));
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