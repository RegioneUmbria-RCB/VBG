using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEDYN2MODELLIT_STORICO per la classe IstanzeDyn2ModelliTStorico il 17/02/2010 10.51.26
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    public partial class IstanzeDyn2ModelliTStoricoMgr : BaseManager
    {
        public IstanzeDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? codiceistanza, int? fk_d2mt_id)
        {
            var c = new IstanzeDyn2ModelliTStorico();


            c.Idcomune = idcomune;
            c.Idversione = idversione;
            c.Codiceistanza = codiceistanza;
            c.FkD2mtId = fk_d2mt_id;

            return (IstanzeDyn2ModelliTStorico)this.db.GetClass(c);
        }

        public List<IstanzeDyn2ModelliTStorico> GetList(IstanzeDyn2ModelliTStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeDyn2ModelliTStorico>();
        }

        public IstanzeDyn2ModelliTStorico Insert(IstanzeDyn2ModelliTStorico cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeDyn2ModelliTStorico ChildInsert(IstanzeDyn2ModelliTStorico cls)
        {
            return cls;
        }




        public IstanzeDyn2ModelliTStorico Update(IstanzeDyn2ModelliTStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeDyn2ModelliTStorico cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeDyn2ModelliTStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }




        private void Validate(IstanzeDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


