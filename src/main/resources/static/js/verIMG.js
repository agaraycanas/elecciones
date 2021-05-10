		 $(window).on("load",(function(){
		 $(function() {
		 $('#id-foto').change(function(e) {addImage(e);});
		function addImage(e){
			var file = e.target.files[0],
			imageType = /image.*/;
			if (!file.type.match(imageType)) return;
			var reader = new FileReader();
			reader.onload = fileOnload;
			reader.readAsDataURL(file);
		}
		function fileOnload(e) {
		var result=e.target.result;
		$('#id-out-foto').attr("src",result);
		}});}));
				