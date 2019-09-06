<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="RegistrazioneCompletataCie.aspx.cs" Inherits="Init.Sigepro.FrontEnd.RegistrazioneCompletataCie" %>

<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" %>
<!DOCTYPE html>
<html>
<head runat="server">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; utf-8" />
    <title>Registrazione effettuata correttamente</title>
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,400italic,600,600italic,700,700italic,900,300italic,300,200italic,200' rel='stylesheet' type='text/css'>
    <link href="~/vendor/bootstrap-3.3.6-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="~/css/jqueryui/ui-bootstrap/jquery-ui-1.10.0.custom.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/ionicons-2.0.1/css/ionicons.min.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/font-awesome-4.6.3/css/font-awesome.min.css" type="text/css" rel="stylesheet" />

    <link href="~/styles/areariservata.css" type="text/css" rel="stylesheet" id="cssLink" />

    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/vendor/bootstrap-3.3.6-dist/js/bootstrap.min.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/vendor/bootstrap-validator-0.11.5/dist/validator.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/app/fix-bottoni.js")%>'></script>

    <script type="text/javascript">

        var banner = $('.banner'),
            nav = $('.main-nav-bar'),
            bannerHeight = banner.height();

        nav.css('margin-top', bannerHeight);

        $(window).on('scroll', function () {
            var scroll = $(this).scrollTop(),
                amount = bannerHeight - scroll;

            if (amount < 0) {
                amount = 0;
            }

            nav.css('margin-top', amount);
        });


    </script>

</head>
<body>
    <form id="form1" runat="server">
        <header class="banner">
            <div class="banner-superiore">
                <div class="container">
                    <div class="row">
                        <div class="col-md-2 logo-comune hidden-xs">
                            <asp:LinkButton runat="server" ID="LinkButton1" OnClick="cmdAccedi_Click" ToolTip="Torna alla pagina iniziale dell'area riservata">
                                <img src='<%= ResolveClientUrl("~/public/handlers/loghi/logo-area-riservata.ashx?&Software=" + Software + "&IdComune=" + IdComune) %>'/ >
                            </asp:LinkButton>
                        </div>

                        <div class="col-md-8 titolo-area-riservata">
                            <h1>Area riservata</h1>

                            <h4>
                                <asp:Literal runat="server" ID="lblNomeComune2">Nome Comune</asp:Literal>
                            </h4>
                        </div>

                        <div class="col-md-2 logo-regione">
                            <img src='<%= ResolveClientUrl("~/public/handlers/loghi/logo-regione.ashx?&Software=" + Software + "&IdComune=" + IdComune) %>' />
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <nav class="navbar navbar-default navbar-fixed-top main-nav-bar">
        </nav>

        <div class="container">

            <h1>Registrazione completata con successo</h1>

            La registrazione è stata effettuata con successo, è ora possibile accedere a tutte le funzionalità dell'area riservata.

			<div class="bottoni">
                <asp:Button runat="server" ID="cmdAccedi" Text="Accedi all'area riservata"
                    OnClick="cmdAccedi_Click" />
            </div>
        </div>
    </form>
</body>
</html>
