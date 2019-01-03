/*****************************************************************************
 *                                                                            *
 *   This program is free software; you can redistribute it and/or modify     *
 *   it under the terms of the GNU General Public License as published by     *
 *   the Free Software Foundation; either version 2 of the License, or        *
 *   any later version.                                                       *
 *                                                                            *
 *****************************************************************************/
/**
 \file     leds.c

 \brief    Funktionen zur Steuerung der LED's.

 Die Port-Pin's zu den LED's werden so manipuliert, dass sie die daran<br>
 angeschlossenen LED's mit Strom versorgen oder eben nicht. Dazu muessen die<br>
 entsprechenden Ports als Ausgang konfiguriert sein. Dies erfolgt entweder<br>
 schon in der Init()-Funktion, oder wird im Falle der BackLED()-Funktion<br>
 dort vorgenommen.

 \see      Defines fuer die Auswahl der ADC-Kanaele in asuro.h<br>
 ON, OFF, GREEN, YELLOW, RED

 \version  V--- - 10.11.2003 - Jan Grewe - DLR<br>
 Original Version von der ASURO CD<br>
 \version  V--- - bis zum 07.01.2007 - <br>
 Bitte in Datei CHANGELOG nachsehen.<br>
 \version  V001 - 13.01.2007 - m.a.r.v.i.n<br>
 +++ Alle Funktionen<br>
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 05.02.2007 - Sternthaler<br>
 +++ Alle Funktionen<br>
 Kommentierte Version (KEINE Funktionsaenderung)


 \file     motor_low.c

 \brief    Low Level Funktionen zur Steuerung der Motoren.

 Die Motorsteuerung erfolgt grundsaetzlich ueber die auf der Asuro-Platine<br>
 aufgebauten H-Bruecken. Dies ist eine Schaltung, ueber die ein Strom in<br>
 verschiedene Richtungen durch die Motoren geleitet werden kann.<br>
 Zur Geschwindigkeitssteuerung werden die beiden im Prozessor vorhandenen<br>
 PWM-Kanaele genutzt, deren Ausgangssignale die Staerke des Stromflusses in<br>
 den H-Bruecken beinflusst.<br>
 Die Initialisierung der PWM-Funktionalitaet erfolgt in der Funktion Init().

 \see      Defines fuer die Auswahl der ADC-Kanaele in asuro.h<br>
 FWD, RWD, BREAK, FREE

 \version  V--- - 10.11.2003 - Jan Grewe - DLR<br>
 Original Version von der ASURO CD<br>
 \version  V--- - bis zum 07.01.2007 - <br>
 Bitte in Datei CHANGELOG nachsehen.<br>
 \version  V001 - 13.01.2007 - m.a.r.v.i.n<br>
 +++ Alle Funktionen<br>
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 05.02.2007 - Sternthaler<br>
 +++ Alle Funktionen<br>
 Kommentierte Version (KEINE Funktionsaenderung)
 \version  V003 - 18.02.2007 - m.a.r.v.i.n<br>
 Datei gesplitted in motor_low.c und motor.c 
 \file     switches.c

 \brief    Funktionen zum lesen der Taster und um den 'Interrupt-Betrieb' ein-<br>
 und auszuschalten. Im 'Interrupt-Betrieb' wird die globale Variable<br>
 switched gesetzt beim betaetigen irgendeines Tasters.

 \see      Define fuer die Auswahl des ADC-Kanals in asuro.h<br>
 SWITCH

 \version  V--- - 10.11.2003 - Jan Grewe - DLR<br>
 Original Version von der ASURO CD<br>
 \version  V--- - bis zum 07.01.2007 - <br>
 Bitte in Datei CHANGELOG nachsehen.<br>
 \version  V001 - 13.01.2007 - m.a.r.v.i.n<br>
 +++ Alle Funktionen<br>
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 22.01.2007 - Sternthaler<br>
 +++ Alle Funktionen<br>
 Kommentierte Version (KEINE Funktionsaenderung)
 \version  V003 - 18.02.2007 - Sternthaler<br>
 +++ StartSwitch()
 Korrektur im Code-Beispiel
 \version  V004 - 20.02.2007 - m.a.r.v.i.n<br>
 Korrekturfaktur aus myasuro.h verwenden
 \version  V005 - 20.01.2008 - m.a.r.v.i.n<br>
 ReadADC Funktion zum Auslesen des A/D Wandlers verwenden<br>
 Anpassungen an ATmega168
 \version  V006 - 21.04.2008 - m.a.r.v.i.n<br>
 Bug Report von RN-User thowil<br>
 - Bugfix in PollSwitch Funktion fuehrt zu staendig neuen Interrupts<br>
 bei Verwendung mit StartSwitch<br>
 - PollSwitch kehrt jetzt sofort ohne A/D Wandlung zurueck<br>
 wenn keine Taste gedrueckt wurde

 \file     adc_low.c

 \brief    Low Level Funktion zum Auslesen der ADC-Wandler.

 \version  V001 - 20.01.2008 - m.a.r.v.i.n<br>
 *****************************************************************************/

package haiku.avr.lib.asuro.lib2_8_0_rc1;

import static haiku.avr.AVRDefines.*;
import static haiku.avr.ATmega8.*;

public class AsuroLib2_8_0_rc1 {
	public static final int OFF = 0;
	public static final int ON = 1;

	public static final int GREEN = 1;
	public static final int RED = 2;
	public static final int YELLOW = 3;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public static final int BAUD_RATE = 2400;

	public static final long MY_SWITCH_VALUE = 61;
	public static final boolean FALSE = false;
	
	public static final int RWD	= (1 << (4));
	public static final int FWD	= (1 << (5));
	public static final int BREAK	= 0x00;

	//ADC
	/**!< ADC5 A/D Wandler Port fuer Batterie Abfrage */
	public static final int BATTERIE	= (1 << (0)) | (1 << (2));
	/**!< ADC4 A/D Wandler Port fuer Tastsensor */
	public static final int SWITCH	= (1 << (2));
	/**!< ADC3 A/D Wandler Port fuer Linienfolger Fototransistor links */
	public static final int IR_LEFT =  (1 << (0)) | (1 << (1));
    /**!< ADC2  A/D Wandler Port fuer Linienfolger Fototransistor rechts */
	public static final int IR_RIGHT = (1 << (1));
    /**!< ADC1 A/D Wandler Port fuer Odometrie Sensor links*/
	public static final int WHEEL_LEFT = (1 << (0));
    /**!< ADC0 A/D Wandler Port fuer Odometrie Sensor rechts */
	public static final int WHEEL_RIGHT = 0;

	
	public static final int LEFT_DIR	= (1 << (4)) | (1 << (5));
	public static final int RIGHT_DIR	= (1 << (4)) | (1 << (5));

	public static final int FRONT_LED	= (1 << (6));
	public static final int ODOMETRIE_LED	= (1 << (7));
	public static final int RED_LED	= (1 << (2));
	public static final int GREEN_LED	= (1 << (0));
	
    static volatile InterruptSimulation interruptSim;

	private static class InterruptSimulation implements Runnable {
        @Override
        public void run() {
            while(true) {
                count36kHz++;
                //SIGNAL_SIG_ADC();
                if (AdcIntFunc != null)
                    AdcIntFunc.run();
             
                //SIGNAL_SIG_INTERRUPT1();
                //SIGNAL_SIG_OUTPUT_COMPARE2();
                
                //SIGNAL_SIG_OVERFLOW2();
                timebase=System.currentTimeMillis();
                if (Ovr2IntFunc!=null)
                    Ovr2IntFunc.run();

            }
        }
	}
	

	/** Odometrie LED an */
	public static void ODOMETRIE_LED_ON() {
		PORTD |= ODOMETRIE_LED;
	}

	/** Odometrie LED aus */
	public static void ODOMETRIE_LED_OFF() {
		PORTD &= ~(ODOMETRIE_LED); 
	}

	/** Rote Status LED an */
	public static void RED_LED_ON() {
		PORTD |= RED_LED;
	}

	/** Gruene Status LED an */
	public static void GREEN_LED_ON() {
		PORTB |= GREEN_LED;
	}

	/** Rote Status LED aus */
	public static void RED_LED_OFF() {
		PORTD &= ~(RED_LED);
	}

	/** Gruene Status LED aus */
	public static void GREEN_LED_OFF() {
		PORTB &= ~(GREEN_LED);
	}

	/**
	 * \brief Flag, dass der Interrupt SIG_INTERRUPT1 durch eine gedrueckte
	 * Taste<br> ausgeloesst wurde. 0 = keine Taste, 1 = Taste gedrueckt.<br> Kann
	 * im eigenen Programm immer \b abgefragt werden.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_INTERRUPT1) in asuro.c<br>
	 * StartSwitch(), StopSwitch(), PollSwitch() in switches.c
	 *****************************************************************************/
	static volatile boolean switched;

	/**
	 * \brief Odometriesensor Zaehler bei Interrupt Betrieb.<br> encoder[0] =
	 * links; encoder[1] = rechts.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_ADC) in asuro.c<br> EncoderInit(),
	 * EncoderStop(), EncoderStart(), EncoderSet(), Go(),<br> Turn() in encoder.c
	 *****************************************************************************/
	public static volatile int encoder[] = new int[2];

	/****************************************************************************
	 * ! \brief Counter fuer 36kHz.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_OVERFLOW2) in asuro.c<br> Gettime(),
	 * Sleep() in time.c
	 * *********************************************************
	 * ******************
	 */
	static volatile int count36kHz;

	/**
	 * ! \brief Sytemzeit in ms.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_OVERFLOW2) in asuro.c<br> Gettime() in
	 * time.c
	 * ********************************************************************
	 * *******
	 */
	static volatile long timebase;

	/***************************************************************************
      \brief
      Interrupt-Funktion fuer den AD-Wandler. Kann auch, ueber autoencode gesteuert,
      die Odometrie-Zaehler in encoder hochzaehlen.
      Bemerkung:
      Alle ADC Werte kommen durch die ADC hardware pipeline 2 Interrupts sp�ter an.
    
      \param
      keine
    
      \return
      nichts
    
      \see
      Die globale Variable autoencode wird hier ausgewertet. Ist sie nicht FALSE,\n
      dann wird der AD-Wandler-Wert zum Zaehlen der Odometriewerte in der globalen\n
      Variablen encoder benutzt.\n
      Es wird auch der AD-Wandler-Kanal auf die 'andere' Seite der Odometrie\n
      umgeschaltete und der AD-Wandler neu gestartet.\n
      Somit wird erreicht, dass zumindest die Odometriemessung automatisch erfolgt.
        Anmerkungen zu encoder:
        1)
        In avg wird ein gleitender Mittelwert mitgef�hrt: a(n+1) = (a(n)+sensor)/4
        (Ich shifte um 2 und habe damit einen Teiler von 4.)
    
        2)
        Ich wollte der Interruptroutine Platz und Zeit ersparen und habe deshalb
        auf eine Initialisierung von avg verzichtet. Durch den kleinen Teiler 4
        passt sich avg sehr rapide den ADC-Werten an. Und verliert in der Realit�t
        auch "nie" den ersten Tick. (So muss ASURO auch meist erst mal anfahren.)
    
        3)
        Allerdings - und hier habe ich noch nicht probiert - k�nnte es mit ein Teiler von 8
        zum ersten mal gelingen, bei Tageslicht, auch ohne eingeschaltetem Encoder-LED,
        Ticks zu sammeln (allerdings verrauscht). Und das spart immerhin Energie -
        falls es wirklich darauf ankommt.
    
      * ADC_vect: handles all ADC and odometry ticking
    
      \par  Beispiel:
    int main(void) {
       int lSoll=60, rSoll=30, lPwm=lSoll, rPwm=rSoll, delta=50;
       Init();
       EncoderInit();
       EncoderStart();
    
       while(1) {
          int lIst=encoder[LEFT],   rIst=encoder[RIGHT];
          EncoderSet(0, 0);
    
          lPwm+=(delta*lSoll - 1000*lIst)/300;
          rPwm+=(delta*rSoll - 1000*rIst)/300;
    
          SetMotorPower(lPwm, rPwm);
          Msleep(delta);
       }
    }
    *****************************************************************************/
    public static Runnable IsrStandard = new Runnable() {
        private int sign[]=new int[] {1,1}, avg[]=new int[2], adc_cnt = 0;
    
        @Override
        public void run() {
            if(autoencode)
            {
                // WHEEL_RIGHT || WHEEL_LEFT
                int sensor=ReadADC(WHEEL_RIGHT+adc_cnt, 0);
                int s=(sensor >> 8);
                // In avg wird ein gleitender Mittelwert mitgef�hrt: a(n+1) = 0.75*a(n)+0.25*s
                avg[adc_cnt] += (s-avg[adc_cnt])>>2;
    
                // Schneidet die aktuelle Sensorkurve den gleitenden Mittelwert? Konkret:
                // Weicht der aktuelle Sensorwert um mehr als +/- 2 vom gleitenden Mittelwert ab?
                if (sign[adc_cnt]*(s-avg[adc_cnt]) > 2)
                {
                   // Dann z�hle einen Tick weiter.
                   // Und n�chster Tick erst wieder bei -/+ 2 Abweichung vom gleitenden Mittelwert.
                   encoder[adc_cnt^RIGHT]++;
                   sign[adc_cnt] = -sign [adc_cnt];
                }
                adc_cnt^=1;
            }
       }
    };
    /**
	 * ! \brief Steuert, ob die Odometrie-Sensor Abfrage im Interrupt Betrieb
	 * laufen soll.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_ADC) in asuro.c<br> EncoderInit(),
	 * EncoderStart(), EncoderStop() in encoder_low.c
	 * ****************************
	 * ***********************************************
	 */
	static volatile boolean autoencode;

	/****************************************************************************
	 * ! \brief Zeiger auf User Funktion die von der Interupt Funktion aus
	 * aufgerufen wird.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_OVERFLOW2) in asuro.c<br> InitRC5(), in
	 * rc5.c
	 * *********************************************************************
	 * ******
	 */
	static volatile Runnable Ovr2IntFunc;

	/****************************************************************************
	 * ! \brief Zeiger auf User Funktion die von der Interupt Funktion aus
	 * aufgerufen wird.
	 * 
	 * \see Interruptfunktion SIGNAL (SIG_ADC) in asuro.c<br> EncoderInit() in
	 * encoder_low.c
	 * *************************************************************
	 * **************
	 */
    static volatile Runnable AdcIntFunc = IsrStandard;

	/**
	 * \brief Schaltet den A/D Multiplexer auf den gewuenschten A/D Kanal<br>
	 * startet die A/D Wandlung und gibt den gelesenen Wert zurueck.
	 * 
	 * \param mux Nummer des A/D Kanal Multiplexer \param sleep optionale
	 * Wartezeit
	 * 
	 * \return 10 Bit A/D Wert (Bereich 0..1023)
	 *****************************************************************************/

	public static int ReadADC(int mux, int sleep) {
		if ((mux) == (BATTERIE))
			ADMUX = (1 << REFS0) | (1 << REFS1) | mux; // interne 2.56V Referenz
		else
			ADMUX = (1 << REFS0) | mux; // Referenz mit externer Kapazitaet
		if (sleep != 0)
			Sleep(sleep);

		ADCSRA |= (1 << ADSC); // Starte AD-Wandlung
		while ((ADCSRA & (1 << ADIF)) == 0)		// Ende der AD-Wandlung abwarten
			;
		ADCSRA |= (1 << ADIF); // AD-Interupt-Flag zuruecksetzen
		return ADC; // Ergebnis als 16-Bit-Wert
	}

	/**
	 * \brief Steuert die (lustige) mehrfarbige Status-LED.
	 * 
	 * \param[in] color Zu setzende Farbe. [ OFF | GREEN | RED | YELLOW ] Bei
	 * einem nicht definierten Wert von 'color' aendert sich nichts an der LED.
	 * 
	 * \return nichts
	 * 
	 * \par Hinweis: Diese Funktion ist als 'inline'-Funktion definiert.
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) 
	 * <pre>// Schaltet die Status-LED abwechselnd auf gruen und rot. 
	 * while (1) {
	 *     StatusLED (GREEN); 
	 *     Msleep (500); 
	 *     StatusLED (RED); 
	 *     MSleep (500); 
	 * }</pre>
	 *****************************************************************************/
	public static void StatusLED(int color) {
		if (color == OFF) {
			GREEN_LED_OFF();
			RED_LED_OFF();
		}
		if (color == GREEN) {
			GREEN_LED_ON();
			RED_LED_OFF();
		}
		if (color == YELLOW) {
			GREEN_LED_ON();
			RED_LED_ON();
		}
		if (color == RED) {
			GREEN_LED_OFF();
			RED_LED_ON();
		}
	}

	/**
	 * \brief Steuert die vorne, nach unten zeigende, Front-LED.
	 * 
	 * \param[in] status Schaltet die LED an bzw. aus. [ ON | OFF ]
	 * 
	 * \return nichts
	 * 
	 * \par Hinweis: Diese Funktion ist als 'inline'-Funktion definiert.
	 * 
	 * \par Achtung: Der uebergeben Parameter wird nicht geprueft, und kann evl.
	 * zu unerwarteten<br> Reaktionen fuehren, da der Port D anhand dieses Wertes
	 * manipuliert wird.
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * schalte die Front-LED an. FrontLED (ON); </pre>
	 *****************************************************************************/
	public static void FrontLED(int status) {
		PORTD = (PORTD & ~(1 << PD6)) | (status << PD6);
	}

	/**
	 * \brief Steuert die beiden hinteren Back-LED's<br> Wenn diese Funktion
	 * aufgerufen wird, funktioniert die Odometriemessung<br> \b nicht mehr, da
	 * die gleichen Port-Pins (Port C:Pin 0 und 1) des Prozessors<br> hierfuer
	 * verwendet werden.
	 * 
	 * \param[in] left Schaltet die linke LED an bzw. aus. [ ON | OFF ]
	 * \param[in] right Schaltet die rechte LED an bzw. aus. [ ON | OFF ]
	 * 
	 * \return nichts
	 * 
	 * \par Hinweis: Obwohl die uebergebenen Parameter nicht geprueft werden,
	 * treten hier keine<br> unerwarteten Reaktionen am Port C auf.<br>
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * Linke LED aus; Rechte LED an BackLED (OFF, ON); </pre>
	 *****************************************************************************/
	public static void BackLED(int left, int right) {
		if (left != 0 || right != 0) {
			PORTD &= ~(1 << PD7); // Rad-LED's OFF
			DDRC |= (1 << PC0) | (1 << PC1); // Port als Output => KEINE Odometrie
			PORTC |= (1 << PC0) | (1 << PC1);
		}
		if (left == 0)
			PORTC &= ~(1 << PC1);
		if (right == 0)
			PORTC &= ~(1 << PC0);
	}

	/**
	 * \brief Gibt die aktuelle Zeit in ms zurueck.
	 * 
	 * Da der Asuro keine Atomuhr hat, ist es die vergangene Zeit seitdem er
	 * eingeschaltet wurde.<br> Genauer: nachdem der Interrupt Timer2 aktiviert
	 * wurde.
	 * 
	 * \return Einschaltzeit in Millisekunden (Bereich: unsigned long
	 * 0..286331153)<br> Das sind ca. 79.5 Stunden. Fuer die, die ihren Asuro also
	 * ohne Solarzellen<br> betreiben, reicht diese Zeitangabe bevor der Accu leer
	 * ist.
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) 
	 * <pre>// Alle 500 ms die Front-LED umschalten.
	 * long zeit; 
	 * byte on_off = 1;
	 * 
	 * zeit = Gettime (); 
	 * while (1) {
	 *  if (Gettime () > zeit + 500) {
	 *   zeit = Gettime (); 
	 *   FrontLED (on_off); 
	 *   on_off ^= 1;
	 *  }
	 * }</pre>
	 *****************************************************************************/
	public static long Gettime() {
        timebase=System.currentTimeMillis();
		return timebase;
	}

	/**
	 * \brief Wartefunktion.
	 * 
	 * Die maximale Wartezeit betraegt 7ms. Fuer laengere Wartezeiten siehe
	 * Msleep().<br> Diese Funktion nutzt den Timer 2-Interrupt um ein
	 * 'zeitgefuehl' zu erhalten.<br> Der Interrupt wird mit 36 kHz, durch die
	 * Init()-Funktion initialisiert,<br> aufgerufen und zaehlt dort die globale
	 * Variablen \b count36kHz weiter.<br> Diese Funktion nutzt diesen Zaehler und
	 * berechnet daraus mit dem uebergeben<br> Parameter den Zeitpunkt wann die
	 * Pausenzeit erreicht ist, Danach bricht sie<br> ab, und im Hauptprogramm ist
	 * eben eine Wartezeit eingelegt worden.
	 * 
	 * \param[in] time36kHz Wartezeit x/36kHz (sec)
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * 1 Millisekunde warten Sleep (36); </pre>
	 *****************************************************************************/
	public static void Sleep(int time36kHz) {
		Msleep(time36kHz/36);
	}

	/**
	 * \brief Wartefunktion in ms.
	 * 
	 * \param[in] dauer Wartezeit in Millisekunden.
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * 1.5 Sekunde warten Msleep (1500); </pre>
	 *****************************************************************************/
	public static void Msleep(int dauer) {
		try {
			Thread.sleep(dauer);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * \brief Steuert die Geschwindigkeit der Motoren.
	 * 
	 * \param[in] left_speed Geschwindigkeit linker Motor (Bereich 0..255)
	 * \param[in] right_speed Geschwindigkeit rechter Motor (Bereich 0..255)
	 * 
	 * \return nichts
	 * 
	 * \see Die Initialisierung der PWM-Funktionalitaet erfolgt in der Funktion
	 * Init().
	 * 
	 * \par Hinweis: Diese Funktion ist als 'inline'-Funktion definiert.
	 * 
	 * \par Arbeitsweise: Ueber die Parameter werden die beiden Kanaele der
	 * PWM-Funktionalitaet im<br> Prozessor angesteuert. Diese Art der
	 * Geschwindigkeitsreglung beruht darauf,<br> dass ein digitaler Output-Pin in
	 * schneller Folge an- und ausgeschaltet wird.<br> Mit dem Parameter wird nun
	 * gesteuert wie \b lange der Strom im \b Verhaeltniss <br> zur Zykluszeit \b
	 * angeschaltet ist.<br> Wird z.B. ein Wert von 150 fuer einen Parameter
	 * uebergeben, dann wird fuer<br> 150 / 255-tel der Zykluszeit der Port auf 1
	 * geschaltet und somit ist die<br> Motorleistung entsprechend reduziert.<br>
	 * Daraus ergibt sich auch dass der Asuro \b noch \b nicht bei einem Wert
	 * von<br> 20 fahren wird, da diese Leistung nicht ausreicht ihn
	 * 'anzuschieben'.<br> (PWM = Pulsweitenmodulation)<br>
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * Setzt die Geschwindigkeit fuer den linken Motor // auf 150 und stoppt den
	 * rechten Motor. MotorSpeed (150, 0); </pre>
	 *****************************************************************************/
	public static void MotorSpeed(int left_speed, int right_speed) {
		OCR1A = left_speed;
		OCR1B = right_speed;
	}

	/**
	 * \brief Steuert die Drehrichtung der Motoren.
	 * 
	 * \param[in] left_dir Richtung des linken Motors [ FWD | RWD | BREAK | FREE
	 * ] \param[in] right_dir Richtung des rechten Motors [ FWD | RWD | BREAK |
	 * FREE ]
	 * 
	 * \return nichts
	 * 
	 * \par Hinweis: Diese Funktion ist als 'inline'-Funktion definiert.
	 * 
	 * \par Arbeitsweise: Ueber die Parameter werden die Port-Pin's zu den
	 * H-Bruecken beider Motoren so<br> gesetzt, dass die jeweils 4 beteiligten
	 * Transitoren einer Bruecke den Strom<br> durch die Motoren entweder - FWD
	 * vorwaerts durchleiten - RWD rueckwaerts durchleiten - BREAK den Motor
	 * kurzschliessen (Bremswirkung) - FREE oder von der Stromversorgung trennen
	 * (Motor laeuft aus)
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * Setze die Richtung fuer den rechten Motor auf Rueckwaerts // und
	 * blockiert den linken Motor. MotorDir (BREAK, RWD); </pre>
	 */
	public static void MotorDir(int left_dir, int right_dir) {
		PORTD = (PORTD & ~((1 << PD4) | (1 << PD5))) | left_dir;
		PORTB = (PORTB & ~((1 << PB4) | (1 << PB5))) | right_dir;
	}

	/**
	 * \file motor.c
	 * 
	 * \brief Funktionen zur Steuerung der Motoren.
	 * 
	 * Die Motorsteuerung erfolgt grundsaetzlich ueber die auf der
	 * Asuro-Platine<br> aufgebauten H-Bruecken. Dies ist eine Schaltung, ueber
	 * die ein Strom in<br> verschiedene Richtungen durch die Motoren geleitet
	 * werden kann.<br> Zur Geschwindigkeitssteuerung werden die beiden im
	 * Prozessor vorhandenen<br> PWM-Kanaele genutzt, deren Ausgangssignale die
	 * Staerke des Stromflusses in<br> den H-Bruecken beinflusst.<br> Die
	 * Initialisierung der PWM-Funktionalitaet erfolgt in der Funktion Init().
	 * 
	 * \see Defines fuer die Auswahl der ADC-Kanaele in asuro.h<br> FWD, RWD,
	 * BREAK, FREE
	 * 
	 * \version sto1 - 07.01.2006 - stochri<br> +++ SetMotorPower()<br> first
	 * implementation \version V--- - bis zum 07.01.2007 - <br> Bitte in Datei
	 * CHANGELOG nachsehen.<br> \version V001 - 13.01.2007 - m.a.r.v.i.n<br> +++
	 * Alle Funktionen<br> Zerlegte Sourcen in einzelne Dateien fuer eine echte
	 * Library. \version V002 - 05.02.2007 - Sternthaler<br> +++ Alle Funktionen<br>
	 * Kommentierte Version (KEINE Funktionsaenderung) \version V003 -
	 * 18.02.2007 - m.a.r.v.i.n<br> Datei gesplitted in motor_low.c und motor.c
	 * 
	 * 
	 * * This program is free software; you can redistribute it and/or modify *
	 * it under the terms of the GNU General Public License as published by *
	 * the Free Software Foundation; either version 2 of the License, or * any
	 * later version. * * sto1 07.01.2006 stochri first implementation
	 * 
	 * control the motors
	 * 
	 * input range: -127 full power backturn 0 stop motors +127 full power
	 * vorward
	 * 
	 * motor direction and speed can be set with one parameter
	 * 
	 * last modification: Ver. Date Author Comments ------- ----------
	 * -------------- --------------------------------- sto1 07.01.2006 stochri
	 * first implementation ------- ---------- --------------
	 * --------------------------------- \brief Steuert die Motorgeschwindigkeit
	 * und Drehrichtung der Motoren.
	 * 
	 * \param[in] leftpwm linker Motor (-r�ckwaerts, + vorwaerts) (Wertebereich
	 * -128...127)
	 * 
	 * \param[in] rightpwm rechter Motor (-r�ckwaerts, + vorwaerts)
	 * (Wertebereich -128...127)
	 * 
	 * \par Hinweis: Da der Wertebereich dieser Funktion nur von -128 bis +127
	 * reicht, aber die<br> urspruengliche Funktion MotorSpeed() einen
	 * Wertebereich bis +255 hat, werden<br> die hier uebergebene Parameter als
	 * Absolutwert mit 2 multipliziert<br> weitergegeben.<br>
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) <pre> //
	 * Setzt die Geschwindigkeit fuer den linken Motor auf 60 (vorwaerts), //
	 * und f�r den rechten Motor auf -60 (rueckwaerts) // Asuro soll auf der
	 * Stelle drehen. SetMotorPower (60, -600); </pre>
	 *****************************************************************************/
	public static void SetMotorPower(int leftpwm, int rightpwm) {
		int left, right;

		if (leftpwm < 0) // Ein negativer Wert fuehrt ...
		{
			left = RWD; // ... zu einer Rueckwaertsfahrt, ...
			leftpwm = -leftpwm; // aber immer positiv PWM-Wert
		} else
			left = FWD; // ... sonst nach vorne, ...
		if (leftpwm == 0)
			left = BREAK; // ... oder bei 0 zum Bremsen.

		if (rightpwm < 0) {
			right = RWD;
			rightpwm = -rightpwm;
		} else
			right = FWD;
		if (rightpwm == 0)
			right = BREAK;

		MotorDir(left, right); // Drehrichtung setzen

		/*
		 * Die Geschwindigkeitsparameter mit 2 multiplizieren, da der
		 * Absolutwert der Parameter dieser Funktion nur genau die Haelfte von
		 * der MotorSpeed()- Funktion betraegt.
		 */
		MotorSpeed(leftpwm * 2, rightpwm * 2);
	}

	/**
	  \brief
	  Tastsensor Abfrage im 'Polling-Betrieb'.

	  \return
	  Tastenwert bitorientiert, K1 = Bit5, K2 = Bit4, K3 = Bit3, K4 = Bit2,
	  K5 = Bit1, K6 = Bit0

	  \see
	  Die globale Variable autoencode wird temporaer auf FALSE gesetzt und am Ende<br>
	  der Funktion mit dem alten Wert restauriert.

	  \par  Hinweis:
	  In dieser Funktion sind 2 Sleep() Aufrufe vorhanden. Sie werden benoetigt<br>
	  damit der Kondensator an der AD-Wandlereinheit genuegend Zeit hat geladen<br>
	  zu werden.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  uint8_t t1, t2;
	  unsigned char text [16];

	  while (1)
	  {
	    t1 = PollSwitch ();
	    t2 = PollSwitch ();
	    // 2x PollSwitch aufrufen und beide Rueckgabewerte auf Gleichheit ueberpruefen
	    if (t1 && t2 && t1 == t2)           // irgendeine Taste gedrueckt 
	    {
	      itoa (t1, text, 10);              // Tastenwert senden 
	      SerPrint (text);
	      SerPrint ("\r\n");                // Zeilenvorschub 
	    }
	    Msleep (500);                       // 0,5 sek warten
	  }
	  </pre>
	*****************************************************************************/
	public static int PollSwitch() {
		int i = ReadADC(SWITCH, 15); // sleep 15 ms -> R�ckw�rtskompatibel?! und
										// wg. timing da draussen und drinnen
										// (IsrStandard())

		/*
		 * Die Original Umrechenfunktion von Jan Grewe - DLR wurde ersetzt durch
		 * eine Rechnung ohne FLOAT-Berechnungen. return ((unsigned char) (((
		 * 1024.0/(float)i - 1.0)) * 61.0 + 0.5));
		 * 
		 * Wert 61L evtl. anpasssen, falls fuer K1 falsche Werte zurueckgegebn
		 * werden.
		 */
		return (int) (((10240000L / (long) i - 10000L) * MY_SWITCH_VALUE + 5000L) / 10000);
	}

	/**
	  \brief
	  'Interrupt-Betrieb' zur Tastsensor Abfrage einschalten.

	  \bug
	  (Sternthaler) Die globale Variable \b switched sollte schon in der Funktion<br>
	  mit FALSE initialisiert werden.

	  \par  Hinweis:
	  Ueber die globale Variable switched kann nach diesem Funktionsaufruf im<br>
	  Hauptprogramm geprueft werden, ob eine Taste gedrueckt wurde und kann dann<br>
	  bei Bedarf die Funktion PollSwitch() aufrufen.<br>

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  StartSwitch ();
	  while (!switched)         // wartet auf Tastendruck
	    ;
	  // an dieser Stelle kann mit Pollswitch gepr�ft werden
	  // welche Taste gedr�ckt wurde, wenn n�tig.
	  switched = FALSE;         // Vorbereitung f�r neuen Aufruf von StartSwitch()
	  </pre>
	*****************************************************************************/
	public static void StartSwitch ()
	{
	}

	/**
	  \brief
	  'Interrupt-Betrieb' zur Tastsensor Abfrage anhalten.

	  \par  Hinweis:
	  Die globale Variable switched wird nicht mehr automatisch bei einem<br>
	  Tastendruck gesetzt.

	  \par  Beispiel:
	  (Nicht vorhanden)
	*****************************************************************************/
	public static void StopSwitch ()
	{
	}
	/**
	 * \brief Initialisiert die Hardware: Ports, A/D Wandler, Serielle
	 * Schnittstelle, PWM<br> Die Init Funktion muss von jeden Programm beim Start
	 * aufgerufen werden
	 * 
	 * \see Die Funktionen Sleep() und Msleep() in time.c werden mit dem hier<br>
	 * eingestellten 36 kHz-Takt betrieben.<br>
	 * 
	 * \par Funktionsweise der Zeitfunktionen: Msleep() ruft Sleep() auf. In
	 * Sleep() wird die globale Variable count36kHz<br> zur Zeitverzoegerung
	 * benutzt. Diese Variable wird jedesmal im Interrupt<br> SIG_OVERFLOW2 um 1
	 * hochgezaehlt.<br> Der Interrupt selber wird durch den hier eingestellten
	 * Timer ausgeloesst.<br> Somit ist dieser Timer fuer die Zeitverzoegerung
	 * zustaendig.
	 * 
	 * \see Die globale Variable autoencode fuer die automatische Bearbeitung
	 * der<br> Odometrie-ADC-Wandler wird hier auf FALSE gesetzt.
	 * 
	 * \par Hinweis zur 36 kHz-Frequenz vom Timer 2 Genau diese Frequenz wird
	 * von dem Empfaengerbaustein benoetigt und kann<br> deshalb nicht geaendert
	 * werden.<br> In der urspruenglichen, vom Hersteller ausgelieferten LIB, war
	 * diese<br> Frequenz allerdings auf 72 kHz eingestellt. Durch eine
	 * geschickte<br> Umkonfigurierung durch waste konnte diese aber halbiert
	 * werden.<br> Sinnvoll ist dies, da der durch diesen Timer2 auch ausgeloesste
	 * Timer-<br> Interrupt dann nur noch die Haelfte an Rechenzeit in Anspruch
	 * nimmt.
	 * 
	 * \par Beispiel: (Nur zur Demonstration der Parameter/Returnwerte) 
	 * <pre> // Die Init()-Funktion MUSS IMMER zu Anfang aufgerufen werden. 
	 * int main(void) {
	 *  int wert;
	 *  Init ();
	 *  while (1) {
	 *   // Dein Programm 
	 *  } 
	 *  return 0; 
	 * }</pre>
	 *****************************************************************************/
	public static void Init() {
		/*
		 * Timer2, zum Betrieb mit der seriellen Schnittstelle, fuer die
		 * IR-Kommunikation auf 36 kHz eingestellt.
		 */
		if (__AVR_ATmega168__) {
			// fast PWM, set OC2A on compare match, clear OC2A at bottom, clk/1
			TCCR2A = _BV(WGM20) | _BV(WGM21) | _BV(COM2A0) | _BV(COM2A1);
			TCCR2B = _BV(CS20);
			// interrupt on timer overflow
			TIMSK2 |= _BV(TOIE2);
		} else {
			// fast PWM, set OC2A on compare match, clear OC2A at bottom, clk/1
			TCCR2 = _BV(WGM20) | _BV(WGM21) | _BV(COM20) | _BV(COM21) | _BV(CS20);
			// interrupt on timer overflow
			TIMSK |= _BV(TOIE2);
		}
		// 36kHz carrier/timer
		OCR2 = 0x91;

		/*
		 * Die serielle Schnittstelle wurde waerend der Boot-Phase schon
		 * programmiert und gestartet. Hier werden die Parameter auf 2400 1N8
		 * gesetzt.
		 */
		if (__AVR_ATmega168__) {
			UBRR0L = (byte)(F_CPU / (BAUD_RATE * 16L) - 1);
			UBRR0H = (byte)((F_CPU / (BAUD_RATE * 16L) - 1) >> 8);
			UCSR0B = (1 << RXEN0) | (1 << TXEN0);
			UCSR0C = (1 << UCSZ00) | (1 << UCSZ01);
		} else {
			UBRRH = (byte)((((F_CPU / BAUD_RATE) / 16) - 1) >> 8); // set baud rate
			UBRRL = (byte)(((F_CPU / BAUD_RATE) / 16) - 1);
			UCSRB = (1 << RXEN) | (1 << TXEN); // enable Rx & Tx
			UCSRC = (1 << URSEL) | (1 << UCSZ1) | (1 << UCSZ0); // config USART; 8N1
		}

		/*
		 * Datenrichtung der I/O-Ports festlegen. Dies ist durch die Beschaltung
		 * der Asuro-Hardware nicht aenderbar. Port B: Seriell Senden;
		 * Richtungsvorgabe Motor links; Takt fuer die Geschwindigkeit beider
		 * Motoren; Grueneanteil-Status-LED Port D: Richtungsvorgabe Motor
		 * rechts; Vordere LED; Odometrie-LED (Radsensor); Rotanteil-Status-LED
		 */
		DDRB = IRTX | RIGHT_DIR | PWM | GREEN_LED;
		DDRD = LEFT_DIR | FRONT_LED | ODOMETRIE_LED | RED_LED;

		/*
		 * PWM-Kanaele OC1A und OC1B auf 8-Bit einstellen. Sie werden fuer die
		 * Geschwindigkeitsvorgaben der Motoren benutzt.
		 */
		TCCR1A = _BV(WGM10) | _BV(COM1A1) | _BV(COM1B1);
		TCCR1B = _BV(CS11); // tmr1-Timer mit MCU-Takt/8 betreiben.

		/*
		 * Einstellungen des A/D-Wandlers auf MCU-Takt/64
		 */
		ADCSRA = _BV(ADEN) | _BV(ADPS2) | _BV(ADPS1);

		/*
		 * Sonstige Vorbereitungen. - Alle LED's ausschalten - Motoren stoppen
		 * und schon mal auf Vorwaerts einstellen. - Globale Variable
		 * autoencoder ausschalten.
		 */
		ODOMETRIE_LED_OFF();
		FrontLED(OFF);
		BackLED(ON, ON);
		BackLED(OFF, OFF);
		StatusLED(GREEN);

		MotorDir(FWD, FWD);
		MotorSpeed(0, 0);

		autoencode = FALSE;

		// Ovr2IntFunc = 0;
		/*
		 * Funktion zum ALLGEMEINEN ZULASSEN von Interrupts.
		 */
		sei();
	}

	/**
	 * \brief Interrupt-Funktion fuer Timer-2-Ueberlauf.
	 * 
	 * \see count36kHz, timebase
	 * 
	 * \par Der zum Timer gehoerende Zaehler TCNT2 wird so justiert, dass damit
	 * die<br> gewuenschten 36 kHz erreicht werden.<br> Fuer die Zeitfunktionen
	 * werden die globalen Variablen count36kHz und<br> timebase hochgezaehlt.
	 * 
	 * \par Die Variable Ovr2IntFunc kann als Zeiger auf eine User Funktion
	 * benutzt werden<br> und wird dann, falls ungleich 0, von der Interrupt
	 * Funktion aus angesprungen.
	 * 
	 * \par Beispiel: (Nicht vorhanden)
	 *****************************************************************************/
    static private void SIGNAL_SIG_OVERFLOW2() {
        TCNT2 += 0x25;
        count36kHz++;
        if (count36kHz==0)
            timebase++;
        if (Ovr2IntFunc!=null)
            Ovr2IntFunc.run();
    }

	/**
	 * being used insted TIMER2_OVF_vect during ultrasonic polling #if
	 * defined(__AVR_ATmega168__) SIGNAL(SIG_OUTPUT_COMPARE2A) #else
	 */
    static private void SIGNAL_SIG_OUTPUT_COMPARE2() {
        count36kHz++;
        if (count36kHz==0)
            timebase++;
    }

	/**
	 * \brief Interrupt-Funktion fuer den externen Interrupt 1.
	 * 
	 * \see switched
	 * 
	 * \par Hier wird 'nur' in der globalen Variablen switched vermerkt, dass
	 * ein<br> Switch (Taster) gedrueckt wurde und dieser Interrupt soeben
	 * aufgetreten ist.<br> Damit dieser Interrupt aber nicht permanent aufgerufen
	 * wird, solange der<br> Taster gedrueckt bleibt, wird die Funktion, dass ein
	 * Interrupt erzeugt wird,<br> ueber StopSwitch() abgeschaltet.<br> Nach einer
	 * Bearbeitung im eigenen Hauptprogramm, muss also die Funktion<br>
	 * StartSwitch() wieder Aufgerufen werden, um einen Tastendruck wieder
	 * ueber<br> einen Interrupt zu erkennen.
	 * 
	 * \par Beispiel: (Nicht vorhanden) 
	 * ************************************************
	 */
    static private void SIGNAL_SIG_INTERRUPT1() {
        switched = true;
        if (__AVR_ATmega168__) {
            EIMSK &= ~_BV(INT1); // Externen Interrupt 1 sperren
        } else {
            GICR &= ~_BV(INT1); // Externen Interrupt 1 sperren
        }
        StopSwitch();
    }

    /**
	 * \brief Interrupt-Funktion fuer den AD-Wandler. Kann ueber autoencode
	 * gesteuert<br> die Odometrie-Zaehler in encoder hochzaehlen.
	 * 
	 * \see Die globale Variable autoencode wird hier ausgewertet. Ist sie nicht
	 * FALSE,<br> dann wird der AD-Wandler-Wert zum Zaehlen der Odometriewerte in
	 * der globalen<br> Variablen encoder benutzt.<br> Es wird auch der
	 * AD-Wandler-Kanal auf die 'andere' Seite der Odometrie<br> umgeschaltete und
	 * der AD-Wandler neu gestartet.<br> Somit wird erreicht, dass zumindest die
	 * Odometriemessung automatisch erfolgt.
	 * 
	 * \par Die Variable AdcIntFunc kann als Zeiger auf eine User Funktion
	 * benutzt werden<br> und wird dann, falls ungleich 0, von der Interrupt
	 * Funktion aus angesprungen.
	 * 
	 * \par Beispiel: (Nicht vorhanden)
	 * ***********************************************************
	 */
    static private void SIGNAL_SIG_ADC() {
        if (AdcIntFunc != null)
            AdcIntFunc.run();
    }

	/**
	  \brief
	  Sendet einen null-terminierten String ueber die serielle Schnittstelle.
	  Im Gegensatz zur SerWrite Funktion wird bei dieser Funktion kein Parameter
	  mit der Anzahl der zu sendenden Zeichne ben�tigt. Es wird einfach bis zum
	  Stringende (0-Character) gesendet.

	  Zum Senden von Rohdaten (keine ASCII Zeichen) sollte sattdessen die Funktion
	  SerWrite verwendet werden.

	  \param[in]
	  data null-terminierter String

	  \see SerWrite

	  \author   stochri

	  \version  sto1 - 07.01.2007 - stochri<br>
	            first implementation
	  \version  V005 - 18.08.2007 - marvin<br>
	            signed char als Parameter wg. Compilerwarnungen

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // Beispiel fuer SerPrint
	  SerPrint ("Hallo ASURO!\r\n");
	  </pre>
	*****************************************************************************/
	public static void SerPrint(String data) {
		char i = 0;

		while (i<data.length() && data.charAt(i) != 0x00)
			UartPutc(data.charAt(i++));
	}
	
	/****************************************************************************
	  \brief
	  Sendet einen einzelnen Character �ber die serielle Schnittstelle

	  \param[in]
	  zeichen auszugebendes Zeichen

	  \author   stochri

	  \version  sto1 - 07.01.2006   stochri<br>
	            first implementation
	  \version  V004 - 25.07.2007 - Sternthaler (Gemeldet von helmut_w)<br>
	            Abschalten des Senders nach der Datenuebertragung zum sparen
	            von Energie.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // 'wunderbaren' Text als einzelne Zeichen senden.
	  UartPutc ('H');
	  UartPutc ('e');
	  UartPutc ('l');
	  UartPutc ('l');
	  UartPutc ('o');
             * Wrong or not thread save:
             * whitout synchronize can not be garantied the loop
             * is executed in thread A but 
             * setMemory8(..) is executed in thread b.
             * Which leads to some data b lost. 
	  </pre>
	*****************************************************************************/
	public static void UartPutc(char zeichen) {
		UCSRB = 0x08; // enable transmitter
		UCSRA |= 0x40; // clear transmitter flag
		while ((UCSRA & 0x20) == 0)
			// wait for empty transmit buffer
			;
		UDR = zeichen;
		while ((UCSRA & 0x40) == 0)
			// Wait for transmit complete flag (TXC)
			;
		UCSRB = 0x00; // disable transmitter / powersave
	}

	public static int UartGetc () {
        UCSRB = (1<<RXEN);                         // Empfaenger einschalten
		while((UCSRA & (1 << RXC)) == 0)
			;
		
		return UDR;
	}

	/****************************************************************************
	  \brief
	  Ausgabe eines Integer Wertes als String ueber die serielle Schnittstelle.

	  \param[in]
	  wert Auszugebender Integer Wert (16Bit)

	  \author   Robotrixer, marvin

	  \version  beta - 31.03.2005 - Robotrixer<br>
	            first implementation
	  \version  2.60 - 28.09.2005 - m.a.r.v.i.n<br>
	            strlen verwenden, anstelle fester Laenge
	  \version  2.61 - 20.11.2006 - m.a.r.v.i.n<br>
	            Initialisierung text String kann zu Fehler<br>
	            beim Flashen mit RS232/IR Adapter fuehren<br>
	            (Bug report von francesco)
	  \version  2.70b- 07.01.2007 - m.a.r.v.i.n<br>
	            SerPrint Funktion anstelle SerWrite verwenden
	  \version  2.70rc2- 09.02.2007 - m.a.r.v.i.n<br>
	            Text Laenge auf 7 erhoeht, fuer Ausgabe negativer Werte
	            (Bug Report von HermannSW)

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // Beispiel fuer zeilenweise Ausgabe der Encoder Werte
	  EncoderInit();
	  While(1)
	  {
	    PrintInt (encoder [0]);
	    SerPrint ("   ");
	    PrintInt (encoder [1]);
	    SerPrint ("\n\r");
	    MSleep (500); //0,5sek. warten
	  }
	  </pre>
	*****************************************************************************/
	public static void PrintInt(int wert) {
		SerPrint(""+wert);
	}


	/****************************************************************************
	  \brief
	  Ausgabe eines Long Wertes als String ueber die serielle Schnittstelle.

	  \param[in]
	  wert Auszugebender Long Wert (32Bit)

	  \author HermannSW, marvin

	  \version  2.70rc2 - 09.02.2007    m.a.r.v.i.n<br>
	            first implementation
	            (Idee von HermannSW)

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // Beispiel fuer Ausgabe der Batterie Werte
	  long data;
	  While(1)
	  {
	    data = Batterie ();
	    PrintLong (data);
	    SerPrint ("\n\r");
	    MSleep (500); //0,5sek. warten
	  }
	  </pre>
	*****************************************************************************/
	public static void PrintLong (long wert)	{
		SerPrint(""+wert);
	}

	/****************************************************************************
	  \brief
	  Senden von Daten ueber die serielle Schnittstelle.
	  
	  Die zu senden Daten werden nicht als 0-terminierter C-String erwartet, sondern<br>
	  es werden grundsaetzlich so viele Zeichen gesendet wie im 2.ten Parameter<br>
	  angegeben werden. Deshalb sollte die Funktion eher zum Senden von Rohdaten
	  verwendet werden (keine ASCII Zeichen)
	  Zum Senden von Strings sollte stattdessen die Funktion SerPrint verwendet werden.
	  Bei der aktuellen WinAVR Version (2007055) gibt es eine Warnung, falls ein 
	  String als 1. Parameter uebergeben wird

	  \version  V003 - 25.07.2007 - Sternthaler (Gemeldet von helmut_w)<br>
	            + Abschalten des Senders nach der Datenuebertragung zum sparen
	              von Energie.<br>
	            + Erklaerungsversuch fuer die Warteschleife am Ende der Funktion.
	  \version  V004 - 31.07.2007 - Sternthaler<br>
	            + Erklaerungsversuch fuer die Warteschleife mit Bezug zum Forum
	              unter http://www.roboternetz.de/

	  \param[in]
	  *data Zu sendende Daten
	  \param[in]
	  length Die Anzahl der zu sendenden Zeichen.

	  \see
	  Die Initialisierung vom Timer 2-Interrupt erfolgt in der Funktion Init().

	  \see SerPrint

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // Senden von Rohdaten ueber die Schnittstelle.
	  unsigned char buf[4] = {0x55, 0xaa, 0xab, 0xcd);
	  SerWrite (buf, 4);
	  </pre>
	*****************************************************************************/
	public static void SerWrite(String data, int length) {
		char i = 0;

		UCSRB = 1 << TXEN; // Sender einschalten
		while (length > 0) // so lange noch Daten da sind
		{
			if ((UCSRA & (1 << UDRE)) != 0) { // warten, bis der
															// Sendebuffer leer
															// ist
				UDR = data.charAt(i++);
				length--;
			}
		}
		while ((UCSRA & (1 << TXC)) == 0)	// abwarten, bis das letzte Zeichen
			; // uebertragen wurde.

		UCSRA |= 1 << TXC; // transmission
															// completed Flag
															// setzen

		UCSRB &= ~(1 << TXEN); // Sender ausschalten / Powersave
	}

	/****************************************************************************
	  \brief
	  Lesen von Daten ueber die serielle Schnittstelle
	  
	  Die empfangenen Daten werden in der als Pointer uebergeben Variable data<br>
	  dem Aufrufer zur verfuegung gestellt.<br>
	  Der Aufrufer bestimmt ueber den Parameter Timeout, ob diese Funktion im<br>
	  'blocking'- oder im 'nonblocking'-Mode laufen soll. Im 'blocking'-Mode<br>
	  bleibt diese Funktion auf alle Faelle so lange aktiv, bis die, uber den<br>
	  Parameter length, geforderte Anzahl Zeichen empfamgen wurde.

	  \param[out]
	  data Zeiger auf die einzulesenden Daten
	  \param[in]
	  length Anzahl der zu lesenden Zeichen
	  \param[in]
	  timeout 0 = blockierender Mode<br>
	          Wird hier ein Wert groesser 0 uebergeben, wird nur eine gewisse Zeit<br>
	          lang versucht ein weiteres Zeichen ueber die Schnittstelle zu empfangen.<br>
	          Kommt in dieser Zeit kein weiteres Zeichen, wird im zurueckgegeben<br>
	          Parameter date das erste Zeichen auf 'T' gesetzt und die Funktion<br>
	          kehrt zum Aufrufer zurueck.<br>
	          Ansonsten wird die Funktion auf alle Faelle verlassen, wenn die als<br>
	          Parameter length geforderte Anzahl Zeichen empfangen werden konnten.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // Empfangen von 5 Zeichen. Aber spaetestens nach 20 Versuchen wieder
	  // im Programm weiter machen.
	  char emfangene_daten [10];

	  SerRead (emfangene_daten, 5, 20);
	  if (emfangene_daten [0] == 'T')
	    SerWrite ("Mist, timeout", 13);
	  else
	    SerWrite ("5 Zeichen empfangen", 19);
	  </pre>
	*****************************************************************************/
	public static void SerRead(byte data[], int length, int timeout) {
		int i = 0;
		long time = 0;
		if (timeout != 0) {
			/*
			 * Die Funktion wird auf alle Faelle, auch ohne empfangene Daten,
			 * wieder verlassen. --> nonblocking mode
			 */
			while (i < length && time++ < timeout) {
				int UDR = UartGetc();
				if (UDR != -1) {
					data[i++] = (byte) UDR;
					time = 0;
				}
			}
			if (time > timeout)
				data[0] = 'T';
		} else {
			/*
			 * Die Funktion wird auf alle Faelle erst nach dem Empfang von der
			 * vorgegebenen Anzahl Zeichen verlassen. blocking mode
			 */
			while (i < length) {
				int UDR = UartGetc();
				if (UDR != -1)
					data[i++] = (byte) UDR;
			}
		}
	}
	
	
	/****************************************************************************
	  \brief
	  Liest die Batteriespannung und gibt sie zurueck.<br>
	  Es erfolgt keine Konvertierung in einen Spannungswert.

	  \return
	  10-Bit-Wert der Batteriespannung (Bereich 0..1023)

	  \par  Die Spannung in Volt kann mit folgende Formel berechnet werden:
	        Umess[V] = (Uref / 2 ^ ADC-Bitsanzahl) * Batterie ()<br>
	        Ubat[V]  = ((R1 + R2) * Umess) / R2

	  Dabei sind:
	  \par
	        Uref = 2.56 Volt<br>
	        ADC-Bitsanzahl = 10 Bit<br>
	        R1 = 12000 Ohm auf der ASURO-Platine<br>
	        R2 = 10000 Ohm auf der ASURO-Platine

	  Oder einfach:
	  \par
	        Ubat[V] = 0,0055 * Battery ()

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  // In Variable wert den 10-Bit-Bateriespannungswert lesen
	  // und bei Unterschreitung von 810 eine alles_stop-Funktion
	  // aufrufen.
	  int wert;
	  wert = Battery();
	  if (wert < 810)             // 810 entsprechen ca. 4,455 Volt
	    alles_stop ();            // Spannung zu klein, Akkus schonen
	  </pre>
	*****************************************************************************/
	public static int Battery() {
		int data = 28 * ReadADC(BATTERIE, 10) / 25; // data=1,12*x dann stimmt
													// Wert von Interrupt ADC
													// mit Poll ADC �berein
		// R�ckw�rtskompatibel?!
		return data;
	}


	/****************************************************************************
	  \brief
	  Liest die Daten der beiden Linienverfolgungssensoren.<br>
	  Die Linien-Beleuchtungs-LED kann sowohl an- als auch ausgeschaltet sein.

	  \param[out]
	  data Zeiger auf die gelesenen Daten:<br>
	       data[0] linker Sensor  (Bereich 0..1023)<br>
	       data[1] rechter Sensor (Bereich 0..1023)

	  \see
	  Die globale Variable autoencode wird temporaer auf FALSE gesetzt und am Ende<br>
	  der Funktion mit dem alten Wert restauriert.

	  \par  Hinweis:
	  Die Linien-Beleuchtungs-LED kann vorher mit der Funktion FrontLED()<br>
	  im aufrufenden Programmteil an- bzw. ausgeschaltet werden.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  unsigned int data [2];
	  FrontLED (ON);
	  LineData (data);
	  if (data [0] > 100)         // 0 ist der linke Sensor ...
	    tu_diese ();              // linker Sensor > 100
	  if (data [1] < 50)          // ... 1 ist der rechte Sensor
	    tu_das ();                // rechter Sensor < 50
	  </pre>
	*****************************************************************************/
	public static void LineData(int[] data) {
		/*
		 * Linken Linien-Sensor lesen
		 */
		data[LEFT] = ReadADC(IR_LEFT, 0);
		/*
		 * Rechten Linien-Sensor lesen
		 */
		data[RIGHT] = ReadADC(IR_RIGHT, 0);
	}


	/***************************************************************************
	  \brief
	  Liest die Daten der beiden Odometriesensoren (Radsensoren).<br>
	  Diese Funktion schaltet die Odometrie-LED's immer an.<br>
	  Diese Funktion schaltet die Back-LED's immer aus.

	  \param[out]
	  data Zeiger auf die gelesenen Daten:<br>
	       data[0] linker Sensor,<br>
	       data[1] rechter Sensor. (Bereich 0..1023)

	  \par  Hinweis:
	  Die Odometrie-Beleuchtungs-LED's muessen zur Messung der Odometrie-<br>
	  sensoren wegen der Hardware im ASURO immer eingeschaltet sein.<br>
	  Die Hardware ist so ausgelegt, dass dadurch allerdings die hinteren<br>
	  Back-LED's ausgeschaltet werden.<br>
	  Da die Odometrie-Beleuchtungs-LED's in dieser Funktion EIN-geschaltet<br>
	  werden, funktionieren dann die Back-LED's nicht mehr. Sie koennen im<br>
	  Programm nach dieser Funktion mit BackLED() bei Bedarf wieder<br>
	  eingeschaltet werden.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
	  unsigned int data [2];
	  OdometryData (data);
	  if (data [0] > data [1])    // 0 linker Sensor; 1 ist rechter Sensor
	    fahre_links ();           // Ein bisschen nach links fahren
	  if (data [0] < data [1])
	    fahre_rechts ();          // Ein bisschen nach rechts fahren
	  BackLED (ON, OFF);          // linke Back-LED mal wieder anschalten
	  </pre>
	*****************************************************************************/
	public static void OdometryData(int[] data) {
		/*
		 * Vorbereitung zum lesen der Odometrie-Sensoren.
		 */
		DDRC &= ~(((1 << PC0) | (1 << PC1))); // Port auf Input=>Back-LEDs
													// gehen aus
		ODOMETRIE_LED_ON(); // Odometrie-LED's einschalten

		/*
		 * Linken Odometrie-Sensor lesen
		 */
		data[LEFT] = ReadADC(WHEEL_LEFT, 0);
		/*
		 * Rechten Odometrie-Sensor lesen
		 */
		data[RIGHT] = ReadADC(WHEEL_RIGHT, 0);
	}
	
	/****************************************************************************
	  \file     encoder_low.c

	  \brief    Low Level Funktionen f�r Radencoder und Odometrie.

	  \see      Defines zum setzen von Port's und Konfigurationen in asuro.h\n
	            TRUE, FALSE, LEFT, RIGHT

	  \version  V--- -
	            KEINE original Version von der ASURO CD vom DLR\n
	  \version  V--- - bis zum 07.01.2007 - \n
	            Bitte in Datei CHANGELOG nachsehen.\n
	            Das Grundgeruest dieser Funktionen wurde von stochri erstellt.\n
	  \version  V001 - 13.01.2007 - m.a.r.v.i.n\n
	            +++ Alle Funktionen\n
	            Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
	  \version  V002 - 27.01.2007 - Sternthaler\n
	            +++ Alle Funktionen\n
	            Kommentierte Version (KEINE Funktionsaenderung)
	  \version  V003 - 21.02.2007 - Sternthaler\n
	            +++ EncoderStart()\n
	            KEINE Funktionsaenderung\n
	            Die Fehlerbeschreibung von Sternthaler ersatzlos gestrichen, da\n
	            die Funktionalitaet von stochri durch das Starten des AD-Wandlers\n
	            in EncoderInit() im sogenannten 'free running'-Mode gegeben ist.
	  \version  V004 - 27.01.2008 - m.a.r.v.i.n\n
	            Die Encoder Interrupt Funktion IsrEnc wird in der EncoderInit\n
	            Funktion �ber die Variable AdcIntFunc in den ADC Interrupt\n
	            eingebunden und von dort aufgerufen

	*****************************************************************************/

	/****************************************************************************
	  \brief
	  Den Interrupt Betrieb der Odometriesensoren-Messung initialisieren und starten.

	  \see
	  autoencode, encoder

	  \par  Funktionsweise:
	  Globale Variable autoencode auf TRUE.\n
	  Diese Funktion wird nun verlassen und das aufrufende Hauptprogramm arbeit\n
	  weiter. In der Zwischenzeit ist der AD-Wandler beschaeftigt um das Mess-\n
	  ergebniss zu ermitteln.\n
	  Ist der Wandler fertig, wird der Interrupt zum AD-Wandler aufgerufen und in\n
	  der dazu vorhandene Interrupt-Funktion aus asuro.c bearbeitet.\n
	  Dort wird nun AUTOMATISCH das Messergebnis ausgewertet, ein erkannter\n
	  Hell- Dunkel-Wechsel an der Encoderscheibe erkannt und dadurch der Zaehler\n
	  in der globalen Variablen encoder[] weitergezaehlt.\n
	  Ausserdem wird dort dann der AD-Wandler fuer die andere Seite gestartet.\n
	  Da dies dann immer im Wechsel laeuft, kann das Hauptprogramm, ohne\n
	  weiters Zutun von nun ab auf die Zaehlerwerte in encoder[] zugreifen.

	  \par  Beispiel:
	  (Nur zur Demonstration der Parameter/Returnwerte)
	  <pre>
    public static void main(String[] args) {
        Init();

        EncoderInit();

        MotorDir(FWD, FWD);
        MotorSpeed(150, 150);
        while (true) {
            // Dein Programm

            if (encoder[0] > 500) {
                EncoderStop();
                MotorSpeed(0, 0);
            }
        }
    }
      </pre>
	*****************************************************************************/
	public static void EncoderInit ()
	{
	  /*
	    Alle definierten Interrupts im Asuro sperren.
	  */
	  cli();

	  if (interruptSim==null) {
	      interruptSim=new InterruptSimulation();
	      new Thread(interruptSim).start();
	  }
	  /*
	    Odometrie im Interruptbetrieb weiter bearbeiten.
	  */
	  autoencode = true;
	  
	  /*
	    Die Odometrie Hell-/Dunkel-Zaehler zuruecksetzen/initialisieren.
	  */
	  EncoderSet (0, 0);

	  /*
	    Alle definierten Interrupts im Asuro wieder zulassen.
	  */
	  sei();

	}

	/****************************************************************************/
	/*!
	  \brief
	  Den Interrupt Betrieb der Odometriesensoren-Messung anhalten.

	  \see 
	  Die globale Variable autoencode hier auf FALSE gesetzt.

	  \par  Funktionsweise:
	  Durch das setzen der globalen Variablen autoencode auf FALSE wird in\n
	  der AD-Wandler-Interruptfunktion der Teil zur Bearbeitung uebersprungen.\n
	  Dadurch wird der Wandler nicht mehr neu gestartet und somit stopp die\n
	  Automatik.

	  \par  Beispiel:
	  (siehe unter EncoderInit bzw. in den examples)
	*****************************************************************************/
	public static void EncoderStop ()
	{
	  autoencode = false;
	}



	/****************************************************************************/
	/*!
	  \brief
	  Den Interrupt Betrieb der Odometriesensoren-Messung starten.

	  \see 
	  Die globale Variable autoencode hier auf TRUE gesetzt.

	  \par Beispiel:
	  (siehe unter EncoderInit bzw. in den examples)
	*****************************************************************************/
	public static void EncoderStart ()
	{
	  autoencode = true;
	}



	/****************************************************************************/
	/*!
	  \brief
	  Interruptbetriebene Odometriesensoren Werte vorbelegen.

	  \param[in]
	  setl Wert fuer links

	  \param[in]
	  setr Wert fuer rechts

	  \par  Hinweis:
	  Initialisiert die beiden Werte in der globalen Variable encoder.\n
	  Normalerweise werden die Zaehlerwerte mit 0 initialisiert. Fuer einige\n
	  Anwendungen kann es sinnvoll sein auch schon bestimmte Werte vorzubelegen.

	  \see
	  Die globale Variable encoder wird hier initialisiert.

	  \par Beispiel:
	  (siehe unter den examples)
	*****************************************************************************/
	public static void EncoderSet (
	  int setl,
	  int setr)
	{
	  encoder [LEFT]  = setl;
	  encoder [RIGHT] = setr;
	}

}
