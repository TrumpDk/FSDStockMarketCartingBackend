package com.ibm.fsd.smc.ms.user.service;

public interface MailService {
    void sendSimpleMail(String to, String subject, String content);
}
