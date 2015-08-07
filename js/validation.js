function isNumberKey(evt)
{
        var charCode = (evt.which) ? evt.which : event.keyCode;
        //alert("Number"+charCode);
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;

        return true;
}

function isCharacterKey(evt)
{
	
        var charCode = (evt.which) ? evt.which : event.keyCode;
      // alert("Char"+charCode);
        if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <=122))
           return true;
       
        return false;
        
}

function validateEmail(inputText)  
{  
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
	   if(inputText.value.match(mailformat))  
	   {  
	       document.forms.emailform();  
	       return true;  
	   }  
	   else  
	   { 
	       document.forms.emailform();  
	       return false;  
	   }  
     
}  

function validatePhoneNumber(inputtxt)  
{  
	var charCode = (inputtxt.which) ? inputtxt.which : event.keyCode;
    //alert("Number"+charCode);
    if (charCode > 31 && (charCode < 48 || charCode > 57))
       return false;

    return true;
}  

function validateUsername(evt)
{
	
        var charCode = (evt.which) ? evt.which : event.keyCode;
      // alert("Char"+charCode);
        if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <=122) || (charCode >=48 && charCode <=57))
           return true;
       
        return false;
}

function validatePassword(evt)
{
	
        var charCode = (evt.which) ? evt.which : event.keyCode;
      // alert("Char"+charCode);
        if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <=122) || (charCode = 35) || (charCode = 42) || (charCode = 63) || (charCode = 64)|| (charCode >=48 && charCode <=57))
           return true;
                	
        return false;
        
        	
}