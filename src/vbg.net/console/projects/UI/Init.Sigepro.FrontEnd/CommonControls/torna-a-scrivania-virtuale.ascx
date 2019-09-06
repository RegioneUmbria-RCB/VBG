<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="torna-a-scrivania-virtuale.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.torna_a_scrivania_virtuale" %>

<script>
    $(function () {
        var el = $("#<%=tornaAScrivania.ClientID%>").on("click", function onElementClick(e) {

            if (!confirm("Sei sicuro di voler tornare alla tua scrivania virtuale?")) {
                e.preventDefault();
                return false;
            }
        });
    });
</script>

<div class="menu-laterale">
    <div>
        <asp:LinkButton runat="server" ID="tornaAScrivania" OnClick="Unnamed_Click">
                        <i class="glyphicon glyphicon-home"></i>

                        <div>Torna alla scrivania virtuale</div>
        </asp:LinkButton>
    </div>
</div>


