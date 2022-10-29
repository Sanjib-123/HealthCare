


 $(document).ready(function () {
        //1. hide error section
        $("#specCodeError").hide();
        $("#specNameError").hide();
        $("#specNoteError").hide();

        //2. define error variable
        var specCodeError = false;
        var specNameError = false;
        var specNoteError = false;

        //3.validate function
        function validate_specCode() {
          var val = $("#specCode").val();
          var exp = /^[A-Z]{4,10}$/;
          if (val == "") {
            $("#specCodeError").show();
           
            $("#specCodeError").html("*<b>Code</b>Can not be empty");
            $("#specCodeError").css("color", "red");
             specCodeError = false;
          } else if (!exp.test(val)) {
            $("#specCodeError").show();
           
            $("#specCodeError").html("*<b>Code</b>must be 4-12 character");
            $("#specCodeError").css("color", "red");
             specCodeError = false;
          } else {
	          var id = 0;//for register
	          if($("#id").val()!=undefined){ //edit page
	             specCodeError = true;
	             id = $("#id").val();
		
	           }
              $.ajax({
	              url :'checkCode',
	              data:{"code":val,"id":id},
	              success:function(respTxt){
		              if(respTxt != ''){
			                $("#specCodeError").show();
                           
                            $("#specCodeError").html(respTxt);
                            $("#specCodeError").css("color", "red");
                             specCodeError = false;
			
		                   }else{
			                $("#specCodeError").hide();
                            specCodeError = true;      
		              }
	             }
              });
          }
          return specCodeError;
        }

        function validate_specName() {
          var val = $("#specName").val();
          var exp = /^[A-Za-z0-9\s\.]{4,60}$/;
          if (val == "") {
            $("#specNameError").show();

            specNameError = false;
            $("#specNameError").html("*<b>Name </b>can not be empty");
            $("#specNameError").css("color", "red");
          } else if (!exp.test(val)) {
            $("#specNameError").show();
            specNameError = false;
            $("#specNameError").html("*<b>Name  </b>must be 4-25 character");
            $("#specNameError").css("color", "red");
          } else {
            $("#specNameError").hide();
            specNameError = true;
          }
          return specNameError;
        }

        function validate_specNote() {
          var val = $("#specNote").val();
          var exp = /^[A-Za-z0-9\s\.\-\,]{10,250}$/;
          if (val == "") {
            $("#specNoteError").show();
            specNoteError = false;
            $("#specNoteError").html("*<b>Note </b>can not be empty");
            $("#specNoteError").css("color", "red");
          } else if (!exp.test(val)) {
            $("#specNoteError").show();
            specNoteError = false;
            $("#specNoteError").html(
              "*<b>Note </b>can have 10 to 150 characters A-Z/a-z/0-9.,-(space) "
            );
            $("#specNoteError").css("color", "red");
          } else {
            $("#specNoteError").hide();
            specNoteError = true;
          }
          return specNoteError;
        }

        //4.action-event
        $("#specCode").keyup(function () {
          $(this).val($(this).val().toUpperCase());
          validate_specCode();
        });
        $("#specName").keyup(function () {
          validate_specName();
        });
        $("#specNote").keyup(function () {
          validate_specNote();
        });

        //5.on submit
        $("#specForm").submit(function () {
          validate_specCode();
          validate_specName();
          validate_specNote();

          if (specCodeError && specNameError && specNoteError) return true;
          else return false;
        });
      });