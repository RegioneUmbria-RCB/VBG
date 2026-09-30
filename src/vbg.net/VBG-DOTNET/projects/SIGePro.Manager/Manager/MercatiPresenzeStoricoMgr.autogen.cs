using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_STORICO per la classe MercatiPresenzeStorico il 30/03/2009 12.43.49
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
    public partial class MercatiPresenzeStoricoMgr : BaseManager
    {
        public MercatiPresenzeStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public MercatiPresenzeStorico GetById(string idcomune, int id)
        {
            var c = new MercatiPresenzeStorico();


            c.Idcomune = idcomune;
            c.Id = id;

            return (MercatiPresenzeStorico)this.db.GetClass(c);
        }

        public List<MercatiPresenzeStorico> GetList(MercatiPresenzeStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<MercatiPresenzeStorico>();
        }

        public MercatiPresenzeStorico Insert(MercatiPresenzeStorico cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private MercatiPresenzeStorico ChildInsert(MercatiPresenzeStorico cls)
        {
            return cls;
        }


        public MercatiPresenzeStorico Update(MercatiPresenzeStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(MercatiPresenzeStorico cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(MercatiPresenzeStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(MercatiPresenzeStorico cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
    }
}


