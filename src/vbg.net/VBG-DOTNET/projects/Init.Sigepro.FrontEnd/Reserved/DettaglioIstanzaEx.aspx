<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DettaglioIstanzaEx.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaEx" Title="Dati istanza" %>

<%@ Register Src="VisuraExCtrl.ascx" TagName="VisuraExCtrl" TagPrefix="uc1" %>

<asp:Content ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">
        <%if (FocusSuMovimenti){ %>
        vbg.ready(() => {
            document.location.hash = "movimenti";

            const el = document.getElementById("movimento<%=IdMovimentoFocus%>");
            
            if (el) {
                el.classList.add('alert-info');
                setTimeout(() => el.classList.remove('alert-info'), 10000);
            }

            const messaggio = `<div class="alert alert-success" role="alert" style='margin: 0 auto;width: 30%;'>
	                            Dati trasmessi correttamente
	                            </div>`;
            const node = document.createElement('div');
            node.style.width = "100%";
            node.style.position = "fixed";
            node.style.zIndex = "98";
            node.style.top = "51px";
            node.classList.add("fade");
            node.innerHTML = messaggio;
            document.body.appendChild(node);

            setTimeout(() => node.style.opacity = 1, 500);
            setTimeout(() => node.style.opacity = 0, 5000);
            setTimeout(() => document.body.removeChild(node), 6000);
        });
        <%} %>

        <%if (UsaPost){%>
            let urlParams = <%= UrlMappa%>;
            console.log(urlParams);
            /*
                creazione del form e dei campi di input con i parametri uno per ogni campo
                form con azione POST
                submit del form
            */
        <%}%>
    </script>

    <style>
        .elemento-lista-movimenti.focus {
            border: 2px solid var(--accent-color);
        }

    </style>

</asp:Content>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="dettaglio-visura">

        <uc1:VisuraExCtrl ID="VisuraExCtrl1" runat="server"></uc1:VisuraExCtrl>

        <asp:Button ID="cmdQuestionario" runat="server" CssClass="btn btn-primary" Text="Questionario di valutazione del servizio" OnClick="cmdQuestionario_Click" />
        <asp:Button ID="cmdAccedi" runat="server" CssClass="btn btn-primary" Text="Accedi per visualizzare i dati completi" OnClick="cmdAccedi_Click" />
        <asp:Button ID="cmdGeneraRiepilogo" runat="server" CssClass="btn btn-primary" Text="Rigenera riepilogo pratica" OnClick="cmdGeneraRiepilogo_Click" />
        <asp:Button ID="cmdScaricaZIP" runat="server" CssClass="btn btn-primary" Text="Scarica documenti pratica" OnClick="cmdScaricaZIP_Click" />
        <asp:Button ID="cmdMostraInMappa" runat="server" CssClass="btn btn-primary mostra-mappa" Text="Mostra in mappa" OnClick="cmdMostraInMappa_Click"/>
        <asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />

    </div>
</asp:Content>
