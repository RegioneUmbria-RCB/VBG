<%@ Page Title="" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="lista.aspx.cs" Inherits="Init.Sigepro.FrontEnd.moduli_fvg.compilazione.lista" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style media="all">
        .schede-da-compilare {
            list-style-type: none;
            margin-bottom: 20px;
        }

        .schede-da-compilare > li{
            margin-left: -34px;
        }
            .schede-da-compilare > li.compilata > a {
                color: #3c763d;
            }

                .schede-da-compilare > li > a:before {
                    position: relative;
                    top: 1px;
                    display: inline-block;
                    font-family: 'Glyphicons Halflings';
                    font-style: normal;
                    font-weight: 400;
                    line-height: 1;
                    -webkit-font-smoothing: antialiased;
                    content:"\270f";
                    padding-right: 5px;
                }

                .schede-da-compilare > li.compilata > a:before {
                    position: relative;
                    top: 1px;
                    display: inline-block;
                    font-family: 'Glyphicons Halflings';
                    font-style: normal;
                    font-weight: 400;
                    line-height: 1;
                    -webkit-font-smoothing: antialiased;
                    content: "\e013";
                    padding-right: 5px;
                }

                .intestazione-lista-moduli {
                    line-height: 2em;
                }
    </style>

    <script>
        <%if (!this.TutteLeSchedeSonoCompilate){%>

        $(function onLoad() {
            var bottoneInvia = $("#<%=cmdInviaDatiAlComune.ClientID%>");

            bottoneInvia.addClass("disabled");

            bottoneInvia.on("click", function onClick(e) {
                e.preventDefault();
                return false;
            })
        });
        <%}%>
    </script>
</asp:Content>


<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <h1>
        <asp:Literal runat="server" ID="lblNomeEndoprocedimento"></asp:Literal>
    </h1>



    <div class="intestazione-lista-moduli">
        <ar:RisorsaTestualeLabel runat="server" ID="intestazioneListaModuliFVG" IdRisorsa="FVG_INTESTAZIONE_LISTA_MODULI" />
    </div>

    <div runat="server" id="divTutteLeSchedeSonoCompilate" visible="true">
        <div class="alert alert-success" role="alert">
            <i class="glyphicon glyphicon-ok"></i>
            Tutti i quadri del modulo sono stati compilati correttamente. Fare click su <strong>"Crea PDF modulo"</strong> per proseguire.
        </div>
    </div>

    <asp:Repeater runat="server" ID="rptListaSchedeDinamiche">

        <HeaderTemplate>
            <ul class="schede-da-compilare">
        </HeaderTemplate>

        <ItemTemplate>
            <li runat="server" class='<%#(bool)Eval("Compilata")? "compilata" : "" %>'>
                <asp:LinkButton runat="server" Text='<%#Eval("Descrizione")%>' CommandArgument='<%#Eval("Id") %>' OnClick="OnSchedaSelezionata" />
            </li>
        </ItemTemplate>

        <FooterTemplate>
            </ul>
        </FooterTemplate>
    </asp:Repeater>

    <div>
        <asp:Button runat="server" ID="cmdInviaDatiAlComune" CssClass="btn btn-primary" Text="Crea PDF modulo" OnClick="cmdInviaDatiAlComune_Click" />
        <asp:LinkButton runat="server" ID="cmdGeneraPdf" CssClass="btn btn-default" Text="Anteprima" OnClick="cmdGeneraPdf_Click" />

    </div>
</asp:Content>
