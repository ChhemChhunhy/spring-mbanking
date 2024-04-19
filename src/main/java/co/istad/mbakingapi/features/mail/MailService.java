package co.istad.mbakingapi.features.mail;

import co.istad.mbakingapi.features.mail.dto.MailRequest;
import co.istad.mbakingapi.features.mail.dto.MailResponse;

public interface MailService {
    MailResponse send(MailRequest mailRequest);
}
