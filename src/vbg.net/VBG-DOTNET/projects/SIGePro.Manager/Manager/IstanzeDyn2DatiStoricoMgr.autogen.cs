using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    //
    // File generato automaticamente dalla tabella ISTANZEDYN2DATI_STORICO per la classe IstanzeDyn2DatiStorico il 17/02/2010 10.52.41
    //
    //						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    //				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    // -
    // -
    // -
    // - 
    //
    //	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    //
    public partial class IstanzeDyn2DatiStoricoMgr : BaseManager
    {
        public IstanzeDyn2DatiStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeDyn2DatiStorico GetById(string idcomune, int? idversione, int? codiceistanza, int? fk_d2mt_id, int? fk_d2c_id, int? indice, int? indice_molteplicita)
        {
            var c = new IstanzeDyn2DatiStorico();


            c.Idcomune = idcomune;
            c.Idversione = idversione;
            c.Codiceistanza = codiceistanza;
            c.FkD2mtId = fk_d2mt_id;
            c.FkD2cId = fk_d2c_id;
            c.Indice = indice;
            c.IndiceMolteplicita = indice_molteplicita;

            return (IstanzeDyn2DatiStorico)this.db.GetClass(c);
        }

        public List<IstanzeDyn2DatiStorico> GetList(IstanzeDyn2DatiStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeDyn2DatiStorico>();
        }

        public IstanzeDyn2DatiStorico Insert(IstanzeDyn2DatiStorico cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeDyn2DatiStorico ChildInsert(IstanzeDyn2DatiStorico cls)
        {
            return cls;
        }

        private IstanzeDyn2DatiStorico DataIntegrations(IstanzeDyn2DatiStorico cls)
        {
            return cls;
        }


        public IstanzeDyn2DatiStorico Update(IstanzeDyn2DatiStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeDyn2DatiStorico cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeDyn2DatiStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeDyn2DatiStorico cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeDyn2DatiStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


