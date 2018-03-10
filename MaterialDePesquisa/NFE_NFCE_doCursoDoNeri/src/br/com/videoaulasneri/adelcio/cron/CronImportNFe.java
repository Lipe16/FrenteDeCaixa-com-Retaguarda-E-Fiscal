package br.com.videoaulasneri.adelcio.cron;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class CronImportNFe {
	static final int time = 20;  //  time in seconds
        static Scheduler schedulerNFe;
        static Trigger triggerNFe;

	public static void executar(int interval, boolean display) {
		try {
                if (schedulerNFe == null || schedulerNFe.isShutdown()) {
                    JobDetail job1 = JobBuilder.newJob(JobNFe.class)
                                    .withIdentity("job1", "group1").build();

                    triggerNFe = TriggerBuilder.newTrigger()
                                    .withIdentity("cronTrigger1", "group1")
                                    .withSchedule(CronScheduleBuilder.cronSchedule("0/" + interval + " * * * * ?"))
                                    .build();

                    schedulerNFe = new StdSchedulerFactory().getScheduler();
                    //System.out.println("\nthese tasks will run for " + time + " seconds . . .\n");
                    schedulerNFe.start();
                    schedulerNFe.scheduleJob(job1, triggerNFe);
//                    if (display) JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Vai monitorar a pasta: \\nfe\\xml\\nfce para Gerar a NFCe(modelo 65) </html>");
			
                } else {
//                    JOptionPane.showMessageDialog(null, "<html><h1 style='font-family: Calibri; font-size: 18pt; color: red;'>O Monitoramento da Importação Automática já está Ativado! . . .</html>");
                }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
        public static void finalizar(boolean display) {
            try {
                String msg = "";
                //JOptionPane.showMessageDialog(null, "estado do job: " + schedulerNFe.isShutdown());
                if (schedulerNFe != null && !schedulerNFe.isShutdown()) {
                    schedulerNFe.shutdown();
                    msg = "<html><h1 style='font-family: Calibri; font-size: 18pt; color: blue;'>Finalizou a espera do arquivo XML para Gerar a NFe . . .</html>";
                } else {
                    msg = "<html><h1 style='font-family: Calibri; font-size: 18pt; color: red;'>O Monitoramento da Importação Automática já está desativado! . . .</html>";
                }
                if (display) JOptionPane.showMessageDialog(null, msg);
           } catch (SchedulerException ex) {
                Logger.getLogger(CronImportNFe.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
	public static void main(String[] args) {
           CronImportNFe ciNFe = new CronImportNFe(); 
           ciNFe.executar(5, true);
        }
}