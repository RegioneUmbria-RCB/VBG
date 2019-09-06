<%@ Page Title="Benvenuto nel sistema di presentazione on-line delle pratiche" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="BenvenutoTares.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.BenvenutoTares" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>


<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	
	<div class="pagina-benvenuto">

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <%if (this.MostraDescrizionePagina)
          {%>
        <div class="jumbotron">
            <p>
                <asp:Literal runat="server" ID="descrizionePagina" />
            </p>
        </div>
        <%} %>

        <asp:Repeater runat="server" ID="rptMenu" OnItemDataBound="rptSubMenu_ItemDataBound">
            <ItemTemplate>
                <div class="row">

                    <asp:Repeater runat="server" ID="rptSubMenu">

                        <ItemTemplate>

                            <div class="col-md-6 feature">
                                <div class="row">
                                    <div class="col-md-4">

                                        <div class="benvenuto-icona">
                                            <div>
                                                <asp:HyperLink runat="server" ID="HyperLink1" NavigateUrl='<%# Eval("Url") %>'>
                                                    <asp:Literal runat="server" ID="literal1" Text='<%# Eval("GlyphIcon") %>' Visible='<%#Eval("UseGliph") %>' />
                                                    <asp:Image runat="server" ID="imgIcona" ImageUrl='<%#Eval("UrlIcona") %>' Visible='<%#Eval("UseIcona") %>' />
                                                </asp:HyperLink>
                                            </div>
                                        </div>

                                        <!--<img data-src="holder.js/150x150" class="img-thumbnail" alt="150x150" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMTUwIiBoZWlnaHQ9IjE1MCIgdmlld0JveD0iMCAwIDE1MCAxNTAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzE1MHgxNTAKQ3JlYXRlZCB3aXRoIEhvbGRlci5qcyAyLjYuMC4KTGVhcm4gbW9yZSBhdCBodHRwOi8vaG9sZGVyanMuY29tCihjKSAyMDEyLTIwMTUgSXZhbiBNYWxvcGluc2t5IC0gaHR0cDovL2ltc2t5LmNvCi0tPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PCFbQ0RBVEFbI2hvbGRlcl8xNTUwY2FmZmU3ZCB0ZXh0IHsgZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQgfSBdXT48L3N0eWxlPjwvZGVmcz48ZyBpZD0iaG9sZGVyXzE1NTBjYWZmZTdkIj48cmVjdCB3aWR0aD0iMTUwIiBoZWlnaHQ9IjE1MCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjUwLjUiIHk9Ijc5LjUiPjE1MHgxNTA8L3RleHQ+PC9nPjwvZz48L3N2Zz4=" data-holder-rendered="true" style="width: 150px; height: 150px;">-->
                                    </div>
                                    <div class="col-md-8">
                                        <h2>
                                            <asp:HyperLink runat="server" ID="hlTitolo" Text='<%# Eval("Titolo") %>' NavigateUrl='<%# Eval("Url") %>' />
                                        </h2>
                                        <asp:Literal runat="server" ID="ltrDescrizioneStep" Text='<%# Eval("DescrizioneEstesa") %>' />
                                    </div>
                                </div>
                            </div>

                        </ItemTemplate>

                    </asp:Repeater>

                </div>

            </ItemTemplate>
        </asp:Repeater>
    </div>

</asp:Content>
