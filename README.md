# AppWorks Enterprise World 2016 Developer Lab service

A simple AppWorks service that provides examples of using the AppWorks service development kit (SDK). We provide a simple API that accepts JSON data via POSTs that result in native push notifications being sent via the AppWorks Gateway.

This Maven project, when built, will produce notifications-echo-service_1.0.0.zip, which can be deployed to the AppWorks Gateway 16.0.1.

# Open Text AppWorks service development kit info

The SDK provides access to the set of utility classes required to interact with an instance of an AppWorks Gateway from an AppWorks Service. It covers AppWorks dedicated service deployment API, which is a limited subset of the full AppWorks administration API.

It currently provides the following features:

- Full set of REST clients for working with the AppWorks Gateway's deployments API
- Centralised configuration setting management (creation, update, handling changes from the AppWorks Gateway console)
- Handling of service life-cycle events such as 'service installed', 'service upgraded' and 'service uninstalled'
- EIM connector and custom authentication response decoration

# Documentation

The SDK documentation is hosted over at the AppWorks developer portal, it can be found at the following location:

<https://developer.opentext.com/awd/resources/articles/15239948/developer+guide+opentext+appworks+16+service+development+kit>

For further information regarding AppWorks 16 push notifications the following article is also available.

<https://developer.opentext.com/awd/resources/articles/15239965/developer+guide+opentext+appworks+16+notifications>

# License

This software is available under the following licenses:

## Open Text End User License Agreement -

<https://developer.opentext.com/awd/resources/articles/15235159/end+user+software+license+agreement+for+open+text+corporation+software>

## Trial Use Agreement -

<https://developer.opentext.com/awd/resources/articles/15235173/trial+use+agreement>
