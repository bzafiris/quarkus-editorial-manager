# editorial-manager

Παράδειγμα στα πλαίσια του μαθήματος Προηγμένες Μέθοδοι Ανάπτυξης Λογισμικού (ΠΜΣ στην Ανάπτυξη και Ασφάλεια Πληροφοριακών Συστημάτων, ΟΠΑ).

Παρουσίαση της πλατφόρμας Quarkus και βασικών APIs όπως το Java Persistence API.

## Δημιουργία νέου project με JPA εξαρτήσεις


Προαπαιτούμενα JDK 17+ και Maven 3.9.5

```
mvn io.quarkus.platform:quarkus-maven-plugin:3.30.1:create \
    -DprojectGroupId=gr.aueb \
    -DprojectArtifactId=edtmgr \
    -Dextensions='rest-jackson,hibernate-orm,hibernate-orm-panache,jdbc-h2'
```




