using Init.Sigepro.FrontEnd.WebControls.FormControls;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    public class VisuraMeseControl : DropDownList
    {
        public VisuraMeseControl()
        {
            this.Load += VisuraMeseControl_Load;
        }

        void VisuraMeseControl_Load(object sender, EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                base.AddItem(String.Empty, string.Empty);

                for (int i = 1; i < 13; i++)
                {
                    var monthName = new DateTime(DateTime.Now.Year, i, 1).ToString("MMMM", CultureInfo.CreateSpecificCulture("it"));

                    base.AddItem(monthName, i.ToString().PadLeft(2, '0'));
                }
            }
        }
    }

    public class VisuraAnnoControl : DropDownList
    {
        public VisuraAnnoControl()
        {
            this.Load += VisuraAnnoControl_Load;
        }

        void VisuraAnnoControl_Load(object sender, EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                base.AddItem(String.Empty, string.Empty);
                var startYear = DateTime.Now.Year;

                for (int i = 0; i < 15; i++)
                {
                    var yyyy = startYear - i;

                    base.AddItem(yyyy.ToString(), yyyy.ToString());
                }
            }
        }
    }
}
