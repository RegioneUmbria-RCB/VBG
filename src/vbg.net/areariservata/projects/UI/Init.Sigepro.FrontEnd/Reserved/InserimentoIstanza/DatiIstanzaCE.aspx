<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="DatiIstanzaCE.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiIstanzaCE" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content runat="server" ID="header" ContentPlaceHolderID="head">
    <script type="text/javascript">

        $(function(){
            if (typeof ValidatorUpdateDisplay != 'undefined') {
                var originalValidatorUpdateDisplay = ValidatorUpdateDisplay;

                ValidatorUpdateDisplay = function (val) {
                    var ctrl = $("#" + val.controltovalidate),
                        className = "ar-form-error";

                    ctrl.toggleClass(className, !val.isvalid);

                    originalValidatorUpdateDisplay(val);
                }
            }

            
           <%if (LimiteCaratteri > 0)
             {%>
            (function () {
                var containerCaratteriRimanenti = $('#caratteri-rimanenti'),
                    placeholder = containerCaratteriRimanenti.find('span'),
                    limite = <%=LimiteCaratteri%>,
                   textarea = $('#<%=Oggetto.Inner.ClientID%>');

                function verificaLunghezza() {
                    var el = textarea,
                        len = el.val().length,
                        delta = limite - len,
                        limiteSuperato = delta < 0,
                        avverti = delta < 5;

                    placeholder.text(delta);

                    containerCaratteriRimanenti.toggleClass('errori', avverti);

                    if (limiteSuperato) {
                        el.val(el.val().slice(0,limite));
                        verificaLunghezza();
                    }
                }

                textarea.on('change keyup paste', function (e) {
                    verificaLunghezza();
                });

                verificaLunghezza();
            }());
           
            <%}%>

        });

    </script>


</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <div class="form-small">
        <ar:TextBox runat="server" ID="Oggetto" Label="Oggetto" TextMode="MultiLine" Rows="8" Required="true" />
        <ar:TextBox runat="server" ID="Note" Label="Note" TextMode="MultiLine" Rows="8" />
    </div>
</asp:Content>
