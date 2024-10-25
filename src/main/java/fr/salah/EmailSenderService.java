package fr.salah;

public class EmailSenderService {
    public boolean sendEmail() {
        System.out.println("Email sent");
        throw new IllegalArgumentException("Email not sent");
    }
}
