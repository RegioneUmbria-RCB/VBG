using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.Utils
{
    public class Delayer
    {
        System.Timers.Timer timer;
        //DateTime timerStarted { get; set; } = DateTime.UtcNow.AddYears(-1);
        const int _interval = 400;

        public void Delay(Func<Task> action, int interval = _interval)
        {
            timer?.Stop();
            timer = new System.Timers.Timer() { Interval = interval, Enabled = false, AutoReset = false };

            timer.Elapsed += async (s, e) =>
            {
                if (timer == null)
                    return;

                timer?.Stop();
                timer = null;

                try
                {
                    await Task.Run(action);
                }
                catch (TaskCanceledException ex)
                {

                }
            };

            timer.Start();
        }

        public void Cancel()
        {
            timer?.Stop();
            timer = null;
        }
    }
}
