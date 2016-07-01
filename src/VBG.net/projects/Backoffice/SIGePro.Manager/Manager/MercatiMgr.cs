using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.ComponentModel;
using Init.SIGePro.Authentication;

namespace Init.SIGePro.Manager 
{
	public class MercatiMgr: BaseManager
	{
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        //per default teniamo conto della configurazione di sigepro dal web.
        int? _posteggiDaGenerare = null;
        #endregion

        public int? PosteggiDaGenerare
        {
            get { return _posteggiDaGenerare; }
            set { _posteggiDaGenerare = value; }
        }

        public MercatiMgr( DataBase dataBase ) : base( dataBase ) {}
		
		public Mercati GetById(String idComune, int codiceMercato )
		{
			Mercati retVal = new Mercati();
			retVal.CodiceMercato = codiceMercato;
			retVal.IdComune = idComune;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Mercati;
			
			return null;
		}
		
		public List<Mercati> GetList(Mercati p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<Mercati> GetList(Mercati p_class, Mercati p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList < Mercati>();
		}

		public void Delete(Mercati p_class)
		{
			db.Delete( p_class) ;
		}

		public Mercati Insert( Mercati p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			p_class = ChildDataIntegrations( p_class );

			ChildInsert( p_class );

			return p_class;
		}

        public Mercati Update(Mercati p_class)
        {
            p_class = DataIntegrations(p_class);

            Validate(p_class, AmbitoValidazione.Update);

            db.Update(p_class);

            return p_class;
        }

		private Mercati DataIntegrations ( Mercati p_class )
		{
			Mercati retVal = ( Mercati ) p_class.Clone();

			if ( string.IsNullOrEmpty( retVal.Software ) )
				throw new RequiredFieldException("MERCATI.SOFTWARE obbligatorio");

            if (string.IsNullOrEmpty(retVal.IdComune))
				throw new RequiredFieldException("MERCATI.IDCOMUNE obbligatorio");

            if (_posteggiDaGenerare.GetValueOrDefault(int.MinValue) > int.MinValue)
                AppendMercatiD(p_class);

			return retVal;
		}
		
		private Mercati ChildDataIntegrations(  Mercati p_class  )
		{
			Mercati retVal = ( p_class.Clone() as Mercati );
			
			#region ii. Integrazione delle classi figlio con i dati della classe padre
			
			#region 1.	Mercati_D
			
			foreach( Mercati_D p_dett in retVal.PosteggiMercato )
			{
				if ( IsStringEmpty( p_dett.IDCOMUNE ) )
					p_dett.IDCOMUNE = retVal.IdComune;
				else if ( p_dett.IDCOMUNE.ToUpper() != retVal.IdComune.ToUpper() )
					throw new Exceptions.IncongruentDataException("MERCATI_D.IDCOMUNE diverso da MERCATI.IDCOMUNE");

                if (p_dett.FKCODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                    p_dett.FKCODICEMERCATO = retVal.CodiceMercato;
                else if (p_dett.FKCODICEMERCATO != retVal.CodiceMercato)
                    throw new Exceptions.IncongruentDataException("MERCATI_D.FKCODICEMERCATO diverso da MERCATI.CODICEMERCATO");
			}

			#endregion

			#region 2.	MercatiStradario
			
			foreach( MercatiStradario p_str in retVal.Stradario )
			{
				if ( IsStringEmpty( p_str.IDCOMUNE ) )
					p_str.IDCOMUNE = retVal.IdComune;
				else if ( p_str.IDCOMUNE.ToUpper() != retVal.IdComune.ToUpper() )
					throw new Exceptions.IncongruentDataException("MERCATISTRADARIO.IDCOMUNE diverso da MERCATI.IDCOMUNE");

				p_str.FKCODICEMERCATO = retVal.CodiceMercato;

			}

			#endregion

			#endregion

			return retVal;
		}

		private void ChildInsert( Mercati p_class )
		{
			foreach( Mercati_D p_dett in p_class.PosteggiMercato )
			{
				Mercati_DMgr merc_d = new Mercati_DMgr( db );
				merc_d.Insert( p_dett );
			}

			foreach( MercatiStradario p_str in p_class.Stradario )
			{
				MercatiStradarioMgr merc_str = new MercatiStradarioMgr( db );
				merc_str.Insert( p_str );
			}
			
		}

		private void Validate(Mercati p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
            if (String.IsNullOrEmpty(p_class.Attivo))
				p_class.Attivo = "0";
		
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( Mercati p_class )
		{

		}

        private void AppendMercatiD( Mercati p_class )
        {
            for (int i = 1; i <= _posteggiDaGenerare.GetValueOrDefault(int.MinValue); i++)
            {
                Mercati_D m = new Mercati_D();
                m.CODICEPOSTEGGIO = i.ToString().PadLeft(_posteggiDaGenerare.ToString().Length, Convert.ToChar("0"));

                p_class.PosteggiMercato.Add(m);
            }
        }
    }
}