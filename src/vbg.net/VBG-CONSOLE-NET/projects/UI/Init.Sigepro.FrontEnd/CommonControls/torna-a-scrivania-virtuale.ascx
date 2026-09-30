<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="torna-a-scrivania-virtuale.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.torna_a_scrivania_virtuale" %>

<script>
    $(function () {
        var el = $("#torna-a-scrivania-virtuale").on("click", function onElementClick(e) {

            if (!confirm("Sei sicuro di voler tornare alla tua scrivania virtuale?\n " +
                "I dati immessi fino ad ora non verranno eliminati e potrai terminare la compilazione della domanda in un secondo momento.")) {
                e.preventDefault();
                return false;
            }
        });
    });
</script>

<div class="menu-laterale">
    <div>
        <a href='<%=this.UrlRitorno %>' id="torna-a-scrivania-virtuale">
            <i class="glyphicon glyphicon-home"></i>

            <div>Torna alla scrivania virtuale</div>
        </a>

    </div>
</div>


