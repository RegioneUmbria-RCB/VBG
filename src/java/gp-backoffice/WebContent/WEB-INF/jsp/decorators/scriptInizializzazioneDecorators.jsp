<%@ include file="../includes/taglibs.jsp" %>
<script type="text/javascript">

    vbg.initialize(() => {
    	vbg.initializeAttenderePrego();
    	vbg.initializeModals();
    	vbg.initializeInputFilters();
    	vbg.inizializzaFieldset();
    	aggiungiTitleALabel();
    });
    
    function aggiungiTitleALabel(){
		document.querySelectorAll(".form-group>label").forEach( label => {
			if(!label.getAttribute('title')){
				label.setAttribute('title',label.innerText);
			}
		});
	}
    
	if(window.Prototype) {
	    delete Object.prototype.toJSON;
	    delete Array.prototype.toJSON;
	    delete Hash.prototype.toJSON;
	    delete String.prototype.toJSON;
	}
    
</script>