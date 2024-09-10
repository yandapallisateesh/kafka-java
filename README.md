# kafka-java
# Email Transport Service

A service for transmitting email messages as soon as possible.

## TABLES
    control.sentemail

## REST API

### Metadata

        Fields:

            Required:
            - externalId  - an ID used to identify the email. Should be unique to the system
            - toAddress   - the address to which to send the email
            - subject     - email subject
            - html|text   - html or text email message body
            - category    - used by service to determine delivery class and account used for sending message (including from address)
            - namespace   - same meaning as Thing Storage Service

            Optional:
            - fromName       - unset by default
            - replyToAddress - unset by default
            - replyToName    - unset by default
            - headers        - map of additional headers to be passed directly to vendor and returned in postbacks
            - metadata       - map of additional data to be used for statistics and returned in postbacks

### Endpoints

        POST /api/v2/emails HTTP/1.1

            Description: Sends an email message

            Required Fields: externalId, toAddress, subject, html|text, category, namespace

            RequestBody:
                {
                    "toAddress" : "theamericanpeople@gmail.com",
                    "externalId" : "54321",
                    "fromName" : "Barack Obama",
                    "namespace" : "lighthouse-client.61024",
                    "subject" : "My Fellow Americans",
                    "replyToAddress" : "replies@whitehouse.gov",
                    "replyToName" : "Replies",
                    "category" : "EMS Email Marketing",
                    "html" : "<html><body><strong>Have a great day!</strong></body></html>",
                    "text" : "Have a great day!",
                    "contactId" : "yc.crmContact.1231212",
                    "metadata" : { "campaignId" : 123123, "fmaId" : 6192371 },
                    "headers" : {
                        "SomeExtraHeader" : "some awesome header content",
                        "White-House-Mailer-Id" : "6192371986123018246"
                    }
                }

            Response:

                HTTP/1.1 201 Created

                {
                    "uri" : "/api/v1/emails/12345"
                }

            Error handling:

            - email addresses will be validated (using Java mail API)
            - missing and/or empty/incomplete fields will result in failure
            - any error in submission will result in TOTAL FAILURE (400-level error)


####        POST /api/v2/emails HTTP/1.1

            Description: Sends an email message with file attachments

            RequestParams: email, file

                email(JsonBody):
                    {
                        "toAddress" : "theamericanpeople@gmail.com",
                        "externalId" : "54321",
                        "fromName" : "Barack Obama",
                        "namespace" : "lighthouse-client.61024",
                        "subject" : "My Fellow Americans",
                        "replyToAddress" : "replies@whitehouse.gov",
                        "replyToName" : "Replies",
                        "category" : "EMS Email Marketing",
                        "html" : "<html><body><strong>Have a great day!</strong></body></html>",
                        "text" : "Have a great day!",
                        "contactId" : "yc.crmContact.1231212",
                        "metadata" : { "campaignId" : 123123, "fmaId" : 6192371 },
                        "headers" : {
                            "SomeExtraHeader" : "some awesome header content",
                            "White-House-Mailer-Id" : "6192371986123018246"
                        }
                    }

                file(Multipart File)

            Response:

                HTTP/1.1 201 CREATED



        GET /api/v1/emails/<1132431331.2.1725955654339@qa2-mesos-alfa-slave18-nr> HTTP/1.1

            Description: Retrieves a previously sent email

            RequestParams: messageId

            Response:

                HTTP/1.1 200 OK

                {
                  "bodyText": "Have a great day!",
                  "x-SMTPAPI": "{\"category\":\"TreatmentOnDemand\",\"unique_args\":{\"unsubscriptionClass\":\"treatment-related\",\"contactId\":\"1\",\"unsubscriptionCategory\":\"TreatmentOnDemand\",\"namespace\":\"lighthouse.client.324\",\"externalId\":\"a3bce1f1-459c-4616-988a-fac8b0d83457\"}}",
                  "namespace": "lighthouse.client.324",
                  "externalId": "a3bce1f1-459c-4616-988a-fac8b0d83457",
                  "From": "Lighthouse \u003Cmailer@messages.lhmailer.com\u003E",
                  "To": "to@to.com",
                  "Message-Id": "\u003C1132431331.2.1725955654339@qa2-mesos-alfa-slave18-nr\u003E",
                  "category": "TreatmentOnDemand",
                  "Date": "Tue, 10 Sep 2024 04:07:34 -0400 (EDT)",
                  "Subject": "Canary test",
                  "MIME-Version": "1.0",
                  "Content-Type": "multipart/mixed; \r\n\tboundary=\"----=_Part_0_1707150451.1725955654180\""
                }


        GET /api/v1/emails/info/1 HTTP/1.1

            Description: Retrieves a previously sent email status

            RequestParams: id

            Response:

                HTTP/1.1 200 OK

                {
                  "present": true
                }


        GET /api/v2/emails?namespace=lighthouse.client.324&externalId=a3bce1f1-459c-4616-988a-fac8b0d83457 HTTP/1.1

            Description: Retrieves a previously sent email

            RequestParams: namespace, externalId

            Response:

                HTTP/1.1 200 OK

                {
                  "bodyText": "Have a great day!",
                  "x-SMTPAPI": "{\"category\":\"TreatmentOnDemand\",\"unique_args\":{\"unsubscriptionClass\":\"treatment-related\",\"contactId\":\"1\",\"unsubscriptionCategory\":\"TreatmentOnDemand\",\"namespace\":\"lighthouse.client.324\",\"externalId\":\"a3bce1f1-459c-4616-988a-fac8b0d83457\"}}",
                  "namespace": "lighthouse.client.324",
                  "externalId": "a3bce1f1-459c-4616-988a-fac8b0d83457",
                  "From": "Lighthouse \u003Cmailer@messages.lhmailer.com\u003E",
                  "To": "to@to.com",
                  "Message-Id": "\u003C1132431331.2.1725955654339@qa2-mesos-alfa-slave18-nr\u003E",
                  "category": "TreatmentOnDemand",
                  "Date": "Tue, 10 Sep 2024 04:07:34 -0400 (EDT)",
                  "Subject": "Canary test",
                  "MIME-Version": "1.0",
                  "Content-Type": "multipart/mixed; \r\n\tboundary=\"----=_Part_0_1707150451.1725955654180\""
                }


        DELETE /api/v2/emails/campaign

            Description: purge campaign emails more than 30 days.

            Response:

                HTTP/1.1 200 OK


        POST /api/v2/emails/persist?messageId=123456&externalId=really-cool-uuid-0247c1f1-7dac-47d2-83a3-c3bcdcc571f4&namespace=royale.cheese.12345&sentDate=2024-08-11&emailJson=test

            Description: Creates dummy record in db

            RequestParams: messageId, externalId, namespace, sentDate, emailJson

            Response:

                HTTP/1.1 201 CREATED



### NOTES:

    - There is no from address in the request. The category is used by the service to determine the delivery class of the email, which in turn is used to determine which email account to send from. The from address matches the domain of the sending account, per the DMARC policy.
    - There are no cc/bcc options on this endpoint. It is a best practice to send one email per recipient, so the recipients will not see any other recipients on the email.
    - If integration tests fail on master, check dev-mock1.dev.yodle.com and confirm if smtp-test-server is running on port 1025, if not restart it by running start-smtp-test-server.sh as yodleapp.

### OPEN QUESTIONS:

    - is there a maximum message size?
    - should the service be synchronous or asynchronous? We think sync.
    - do we need to support the addition of custom headers? Yes.
    - do we need to support sending multiple distinct e-mails at once? what are the use cases? No.
    - should ordering of messsages be deterministic? No.
    - how do we generate the message ID in the response? Using the Java mail API.
    - do we want to support attachments? supported in V2.
    - do we want to support embedded images? Not in V1 and V2.
