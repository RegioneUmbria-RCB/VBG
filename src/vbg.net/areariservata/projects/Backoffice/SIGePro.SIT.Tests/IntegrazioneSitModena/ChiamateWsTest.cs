using Init.SIGePro.Sit.Modena;
using Init.SIGePro.Sit.Modena.ElencoMappaliUrbani;
using Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani;
using Init.SIGePro.Sit.Utils;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SIGePro.SIT.Tests.IntegrazioneSitModena
{
    [TestClass]
    public class ChiamateWsTest
    {

        [TestMethod]
        public void ValidaRicercaElencoSubResponseKO()
        {
            string xmlResponseKO = @"<?xml version=""1.0"" encoding=""ISO-8859-1"" standalone=""yes""?>
                    <RispostaRicercaMappaleUrbano xmlns:ns2=""http://www.sigmater.it/ambitiamministrativi"" xmlns=""http://elisa.it/aci"" xmlns:ns4=""http://www.sigmater.it"" xmlns:ns3=""http://www.sigmater.it/catasto"" xmlns:ns5=""http://www.opengis.net/gml"" xmlns:ns6=""http://www.w3.org/1999/xlink"" xmlns:ns7=""http://www.w3.org/2001/SMIL20/"" xmlns:ns8=""http://www.w3.org/2001/SMIL20/Language"">
                        <ErroreRicercaMappaleUrbano>
                            <RichiestaRicercaMappaleUrbano>
                                <IdEnte>F257</IdEnte>
                                <ns3:IdentificativoParzialeUIU>
                                    <ns3:Foglio>0140</ns3:Foglio>
                                    <ns3:Mappale>1000</ns3:Mappale>
                                </ns3:IdentificativoParzialeUIU>
                            </RichiestaRicercaMappaleUrbano>
                            <RicercaFallita></RicercaFallita>
                        </ErroreRicercaMappaleUrbano>
                    </RispostaRicercaMappaleUrbano>";

            var retVal = SerializationExtensions.XmlDeserializeFromString<RispostaRicercaMappaleUrbanoType>(xmlResponseKO);
            Assert.Fail();
        }

        [TestMethod]
        public void ValidaRicercaElencoSubResponseOk()
        {
            string xmlResponseOK = @"<?xml version=""1.0"" encoding=""ISO-8859-1"" standalone=""yes""?>
             <RispostaRicercaMappaleUrbano xmlns:ns2=""http://www.sigmater.it/ambitiamministrativi"" xmlns=""http://elisa.it/aci"" xmlns:ns4=""http://www.sigmater.it"" xmlns:ns3=""http://www.sigmater.it/catasto"" xmlns:ns5=""http://www.opengis.net/gml"" xmlns:ns6=""http://www.w3.org/1999/xlink"" xmlns:ns7=""http://www.w3.org/2001/SMIL20/"" xmlns:ns8=""http://www.w3.org/2001/SMIL20/Language"">
                <RichiestaRicercaMappaleUrbano>
                    <IdEnte>F257</IdEnte>
                    <ns3:IdentificativoParzialeUIU>
                        <ns3:Foglio>0140</ns3:Foglio>
                        <ns3:Mappale>0045</ns3:Mappale>
                    </ns3:IdentificativoParzialeUIU>
                </RichiestaRicercaMappaleUrbano>
                <ns3:S3RispostaConsultUIU dataUltAggAreaSincrona=""2019-01-29"">
                    <ns3:Comune>
                        <ns2:CodiceBelfioreComune>F257</ns2:CodiceBelfioreComune>
                        <ns2:IdentificativoIstatComune>
                            <ns2:CodiceISTATComune>023</ns2:CodiceISTATComune>
                            <ns2:CodiceISTATProvincia>36</ns2:CodiceISTATProvincia>
                        </ns2:IdentificativoIstatComune>
                        <ns2:NomeComune>MODENA</ns2:NomeComune>
                        <ns2:SiglaProvincia>MO</ns2:SiglaProvincia>
                        <ns2:NomeProvincia>MODENA</ns2:NomeProvincia>
                        <ns2:DescrizioneStatoComune>VIGENTE</ns2:DescrizioneStatoComune>
                        <ns2:CapPrincipale>0</ns2:CapPrincipale>
                    </ns3:Comune>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>1</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>16.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>77.68</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>77.68</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832643</ns3:IDMutazione>
                            <ns3:NumeroNota>152261</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>2</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>24.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>116.51</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>116.51</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>1</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832644</ns3:IDMutazione>
                            <ns3:NumeroNota>152262</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>3</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>14.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>67.97</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>67.97</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832645</ns3:IDMutazione>
                            <ns3:NumeroNota>152263</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>4</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832646</ns3:IDMutazione>
                            <ns3:NumeroNota>152264</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>5</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832647</ns3:IDMutazione>
                            <ns3:NumeroNota>152265</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>6</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832648</ns3:IDMutazione>
                            <ns3:NumeroNota>152266</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>7</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832649</ns3:IDMutazione>
                            <ns3:NumeroNota>152267</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>8</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832650</ns3:IDMutazione>
                            <ns3:NumeroNota>152268</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>9</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>16.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>77.68</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>77.68</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832651</ns3:IDMutazione>
                            <ns3:NumeroNota>152269</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>10</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>19.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>92.24</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>92.24</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2012-04-24</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2496848</ns3:IDMutazione>
                            <ns3:NumeroNota>5963</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2012</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2012-04-24</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2012-04-24</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VAR</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>RETT TOPON.PROT53966/12</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>11</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>19.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>92.24</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>92.24</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2012-07-06</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2513193</ns3:IDMutazione>
                            <ns3:NumeroNota>9908</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2012</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2012-07-06</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2012-07-06</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VAR</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>RETT TOPON PROT 87820/12</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>12</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>15.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>72.82</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>72.82</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832654</ns3:IDMutazione>
                            <ns3:NumeroNota>152272</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>13</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832655</ns3:IDMutazione>
                            <ns3:NumeroNota>152273</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>14</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>11.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832656</ns3:IDMutazione>
                            <ns3:NumeroNota>152274</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>15</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>11.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832657</ns3:IDMutazione>
                            <ns3:NumeroNota>152275</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>16</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>11.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832658</ns3:IDMutazione>
                            <ns3:NumeroNota>152276</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>17</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>11.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>53.4</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2013-06-03</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2596816</ns3:IDMutazione>
                            <ns3:NumeroNota>14416</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2013</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2013-06-03</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2013-06-03</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>18</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>12.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>58.26</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832660</ns3:IDMutazione>
                            <ns3:NumeroNota>152278</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>19</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>24.0</ns3:Consistenza>
                            <ns3:FlagClassamento>5</ns3:FlagClassamento>
                            <ns3:Superficie>27</ns3:Superficie>
                            <ns3:RenditaInEuro>116.51</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>116.51</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2007-11-15</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2065763</ns3:IDMutazione>
                            <ns3:NumeroNota>11851</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2007</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2007-11-15</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2007-11-15</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VCL</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE NEL CLASSAMENTO</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI CLASSAMENTO</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>20</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/6</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Box o posti auto pertinenziali; Autosilos, autorimesse (non pertinenziali),parcheggi a raso aperti al pub; Stalle, scuderie e simili.</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>14.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>67.97</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>67.97</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832662</ns3:IDMutazione>
                            <ns3:NumeroNota>152280</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>21</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>477.72</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>477.72</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>1</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832663</ns3:IDMutazione>
                            <ns3:NumeroNota>152281</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>22</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>1</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832664</ns3:IDMutazione>
                            <ns3:NumeroNota>152282</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>23</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                            <ns3:Piano2>1</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832665</ns3:IDMutazione>
                            <ns3:NumeroNota>152283</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>24</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>6.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>573.27</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>573.27</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Interno1>4</ns3:Interno1>
                            <ns3:Piano1>1</ns3:Piano1>
                            <ns3:Piano2>6</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832666</ns3:IDMutazione>
                            <ns3:NumeroNota>152284</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>25</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>4.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>429.95</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>429.95</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                            <ns3:Piano2>2</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832667</ns3:IDMutazione>
                            <ns3:NumeroNota>152285</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>26</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>6.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>621.04</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>621.04</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>1</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832668</ns3:IDMutazione>
                            <ns3:NumeroNota>152286</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>27</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>2</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832669</ns3:IDMutazione>
                            <ns3:NumeroNota>152287</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>28</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                            <ns3:Piano2>2</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832670</ns3:IDMutazione>
                            <ns3:NumeroNota>152288</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>29</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>3</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832671</ns3:IDMutazione>
                            <ns3:NumeroNota>152289</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>30</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                            <ns3:Piano2>3</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832672</ns3:IDMutazione>
                            <ns3:NumeroNota>152290</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>31</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>3</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832673</ns3:IDMutazione>
                            <ns3:NumeroNota>152291</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>32</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2013-06-03</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                            <ns3:Piano2>3</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2596816</ns3:IDMutazione>
                            <ns3:NumeroNota>14416</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2013</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2013-06-03</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2013-06-03</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>33</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>4.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>429.95</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>429.95</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>4</ns3:Piano1>
                            <ns3:Piano2>6</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832675</ns3:IDMutazione>
                            <ns3:NumeroNota>152293</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>34</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>7.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>668.81</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>668.81</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T4</ns3:Piano1>
                            <ns3:Piano2>6</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832676</ns3:IDMutazione>
                            <ns3:NumeroNota>152294</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>35</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>4</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832677</ns3:IDMutazione>
                            <ns3:NumeroNota>152295</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""false"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>36</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                                <ns3:FinePeriodo>2006-11-15</ns3:FinePeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>4</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832678</ns3:IDMutazione>
                            <ns3:NumeroNota>152296</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                        <ns3:DatiConclusione>
                            <ns3:IDMutazione>1974431</ns3:IDMutazione>
                            <ns3:NumeroNota>11520</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2006</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2006-11-15</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2006-11-15</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>FUS</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>FUSIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>FUSIONE</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiConclusione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>37</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2012-04-24</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>5</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2496854</ns3:IDMutazione>
                            <ns3:NumeroNota>5965</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2012</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2012-04-24</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2012-04-24</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VAR</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>RETT TOPON PROT N.53966/12</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>38</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>5</ns3:Piano1>
                            <ns3:Piano2>6</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832680</ns3:IDMutazione>
                            <ns3:NumeroNota>152298</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>39</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2012-07-06</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>5</ns3:Piano1>
                            <ns3:Piano2>6</ns3:Piano2>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2513193</ns3:IDMutazione>
                            <ns3:NumeroNota>9908</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2012</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2012-07-06</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2012-07-06</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VAR</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>RETT TOPON PROT 87820/12</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>40</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>01</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>525.49</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>5</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>2</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832682</ns3:IDMutazione>
                            <ns3:NumeroNota>152300</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>41</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>6.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>13.94</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>13.94</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832683</ns3:IDMutazione>
                            <ns3:NumeroNota>152301</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>42</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>8.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>18.59</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>18.59</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832684</ns3:IDMutazione>
                            <ns3:NumeroNota>152302</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>44</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>13.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>30.21</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>30.21</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2012-04-24</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2496857</ns3:IDMutazione>
                            <ns3:NumeroNota>5967</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2012</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2012-04-24</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2012-04-24</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VAR</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>RETT TOPON PROT N.53966/12</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>45</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>28.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>65.07</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>65.07</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832686</ns3:IDMutazione>
                            <ns3:NumeroNota>152304</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>46</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>34.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>79.02</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>79.02</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2013-06-03</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2596816</ns3:IDMutazione>
                            <ns3:NumeroNota>14416</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2013</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2013-06-03</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2013-06-03</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""false"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>48</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>19.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>44.16</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>44.16</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                                <ns3:FinePeriodo>2006-11-15</ns3:FinePeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832688</ns3:IDMutazione>
                            <ns3:NumeroNota>152306</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                        <ns3:DatiConclusione>
                            <ns3:IDMutazione>1974431</ns3:IDMutazione>
                            <ns3:NumeroNota>11520</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2006</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2006-11-15</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2006-11-15</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>FUS</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>FUSIONE</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>FUSIONE</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiConclusione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>49</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>19.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>44.16</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>44.16</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832689</ns3:IDMutazione>
                            <ns3:NumeroNota>152307</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>50</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>8.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>18.59</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>18.59</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832690</ns3:IDMutazione>
                            <ns3:NumeroNota>152308</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>52</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>9.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>20.92</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>20.92</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832691</ns3:IDMutazione>
                            <ns3:NumeroNota>152309</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>53</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>11.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>25.56</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>25.56</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>11</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832692</ns3:IDMutazione>
                            <ns3:NumeroNota>152310</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>55</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>C/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Magazzini e locali deposito (cantine e soffitte con rendite autonome)</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>05</ns3:ClasseUIU>
                            <ns3:Consistenza>10.0</ns3:Consistenza>
                            <ns3:RenditaInEuro>32.02</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>32.02</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2005-02-23</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>T</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>1832693</ns3:IDMutazione>
                            <ns3:NumeroNota>152311</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2005</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2005-02-23</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2005-02-23</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VTO</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE TOPONOMASTICA</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI TOPONOMASTICA</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                    <ns3:UIURicercata ImgPlanimetriaPresenti=""true"">
                        <ns3:IdentificativoParzialeUIU>
                            <ns3:Foglio>140</ns3:Foglio>
                            <ns3:Mappale>45</ns3:Mappale>
                            <ns3:Subalterno>56</ns3:Subalterno>
                        </ns3:IdentificativoParzialeUIU>
                        <ns3:DatiClassamentoUIU>
                            <ns3:Zona>002</ns3:Zona>
                            <ns3:Categoria>A/2</ns3:Categoria>
                            <ns3:DescrizioneCategoria>Abitazione civile</ns3:DescrizioneCategoria>
                            <ns3:ClasseUIU>03</ns3:ClasseUIU>
                            <ns3:Consistenza>5.5</ns3:Consistenza>
                            <ns3:FlagClassamento>5</ns3:FlagClassamento>
                            <ns3:Superficie>129</ns3:Superficie>
                            <ns3:RenditaInEuro>738.53</ns3:RenditaInEuro>
                            <ns3:RenditaInEuroAlPeriodo>
                                <ns3:RenditaInEuro>738.53</ns3:RenditaInEuro>
                                <ns3:InizioPeriodo>2007-11-15</ns3:InizioPeriodo>
                            </ns3:RenditaInEuroAlPeriodo>
                        </ns3:DatiClassamentoUIU>
                        <ns3:IndirizziCatastaliUIU>
                            <ns3:IndirizzoUIU>
                                <ns3:Indirizzo>VIA ALESSANDRO VOLTA</ns3:Indirizzo>
                                <ns3:Civico>41</ns3:Civico>
                                <ns3:Strada>
                                    <ns3:CodStrada>43</ns3:CodStrada>
                                    <ns3:Dizione>ALESSANDRO VOLTA</ns3:Dizione>
                                </ns3:Strada>
                            </ns3:IndirizzoUIU>
                        </ns3:IndirizziCatastaliUIU>
                        <ns3:Ubicazione>
                            <ns3:Piano1>4-6</ns3:Piano1>
                        </ns3:Ubicazione>
                        <ns3:DatiGenerazione>
                            <ns3:IDMutazione>2065763</ns3:IDMutazione>
                            <ns3:NumeroNota>11851</ns3:NumeroNota>
                            <ns3:DescrizioneTipoNota>Variazione</ns3:DescrizioneTipoNota>
                            <ns3:TipoNota>V</ns3:TipoNota>
                            <ns3:AnnoNota>2007</ns3:AnnoNota>
                            <ns3:ProgressivoNota>1</ns3:ProgressivoNota>
                            <ns3:DataEfficacia>2007-11-15</ns3:DataEfficacia>
                            <ns3:DataRegistrazione>2007-11-15</ns3:DataRegistrazione>
                            <ns3:CodiceCausale>VCL</ns3:CodiceCausale>
                            <ns3:DescrizioneCodiceCausale>VARIAZIONE NEL CLASSAMENTO</ns3:DescrizioneCodiceCausale>
                            <ns3:DescrizioneAttoMutazione>VARIAZIONE DI CLASSAMENTO</ns3:DescrizioneAttoMutazione>
                        </ns3:DatiGenerazione>
                    </ns3:UIURicercata>
                </ns3:S3RispostaConsultUIU>
            </RispostaRicercaMappaleUrbano>";

            var retVal = SerializationExtensions.XmlDeserializeFromString<RispostaRicercaMappaleUrbanoType>(xmlResponseOK);
            Assert.Fail();


        }

        [TestMethod]
        public void VerificaChiamataOk()
        {
            string url = "http://lnx088:8280/aci-web/AciServletXML";
            string requestXml = @"<?xml version=""1.0"" encoding=""ISO-8859-1"" standalone=""yes""?>
                                <RichiestaRicercaMappaleUrbano xmlns=""http://elisa.it/aci"" xmlns:s3=""http://www.sigmater.it"" xmlns:ct=""http://www.sigmater.it/catasto"" xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">
	                                <IdEnte>F257</IdEnte>
	                                <ct:IdentificativoParzialeUIU>
		                                <ct:Foglio>0140</ct:Foglio>
		                                <ct:Mappale>0045</ct:Mappale>
	                                </ct:IdentificativoParzialeUIU>
                                </RichiestaRicercaMappaleUrbano>";

            string nomeServizio = "RicercaMappaleUrbanoService";

            var srv = new SitServiceWrapper(url);
            var response = srv.GetDati<RispostaRicercaMappaleUrbanoType>(requestXml, nomeServizio);

            if (response.ErroreRicercaMappaleUrbano != null)
            {
                Assert.Fail("Nessun risultato per la ricerca");
            }

            var collection = response.S3RispostaConsultUIU.UIURicercata.Select(x => x.IdentificativoParzialeUIU.Subalterno);

            Assert.IsTrue(true);
        }

        [TestMethod]
        public void VerificaValidazioneOk()
        {
            string url = "http://lnx088:8280/aci-web/AciServletXML";

            var adapter = new RichiestaValidazioneMappaliUrbanoAdapter();
            var request = adapter.Adatta("F257", "140", "45", "1");

            var srv = new SitServiceWrapper(url);
            var response = srv.GetDati<RispostaValidazioneMappaliUrbanoType>(request, adapter.NomeServizio);

            if (!response.MappaliUrbanoValidati.MappaleUrbanoValidato.Valido)
            {
                Assert.Fail("Non trovato");
            }

            Assert.IsTrue(true);

        }

        [TestMethod]
        public void VerificaValidazioneKo()
        {
            string url = "http://lnx088:8280/aci-web/AciServletXML";

            var adapter = new RichiestaValidazioneMappaliUrbanoAdapter();
            var request = adapter.Adatta("F257", "140", "45", "1000");

            var srv = new SitServiceWrapper(url);
            var response = srv.GetDati<RispostaValidazioneMappaliUrbanoType>(request, adapter.NomeServizio);

            if (!response.MappaliUrbanoValidati.MappaleUrbanoValidato.Valido)
            {
                Assert.IsTrue(true);
            }


            Assert.Fail("Trovato, non doveva trovarlo");
        }
    }
}
