# 💰 Budgethanteraren 💰

### 🪙 Kort beskrivning av lösningen
Jag lärde mig från föregående labb att inte göra så långa metoder, har försökt hålla det kort och få en högre 
läsbarhet. Haft i åtanke att en klass ska ha ett tydligt syfte och att en metod helst ska utföra endast en uppgift.
Utöver det som var specat i uppgiften valde jag att skapa en huvudklass med namnet BudgetApplication som kör hela
programmet och innehåller alla metoder rörande hanteringen av transaktioner (förutom record Transaction) som ligger
utanför för ökad tydlighet. Alla beräkningar är även placerade i en klass som inte var specad, Calculations.
Mitt arbetssätt var att jag skrev stora delar av den funktionella koden innan jag började göra JUnit-tester. När
testningen kändes omfattande gick jag vidare med att få filläsningen på plats, sen loggning, sen att få ihop den
interaktiva menyn och testköra programmet manuellt.

### 🪙 Icke-buggen
Det jag är mest tacksam för att jag kom på är tyvärr inte en bugg. Men jag har en fundering på scenariot ändå! Ifall en
användare skulle ha en fil vid start med 10st transaktioner, lägger till 3, sparar filen och sen trycker på "visa alla
transaktioner" så skulle bara 3st transaktioner visas. Detta kom jag på när jag funderade på hur programmet skulle
pusslas ihop, efter att ha läst dokumentationen. Även om jag inte hade förutsett problemet vet jag inte hur en logger 
eller debug-run med breakpoints skulle ha kunnat hjälpt vid detta scenario? Hade gärna velat få input om det men 
förstår om det inte är möjligt.

#### 🪙 Den sista (förhoppningsvis) överlevande buggen
När jag skulle kolla igenom arbetet för inlämning så noterade jag att det fanns en utskrift om att transaktionen var
tillagd av användaren, fast utanför try/catch-blocket så den printades även om transaktionen <ins> inte </ins> lades 
till. Inget som flaggades vid loggningen, inget jag hade tänkt på i terminalen vid manuell körning och inget som något 
test plockade upp. Upptäckte det enbart när jag gick igenom och kollade så det var kommentarer på alla metoder, och 
gick igenom koden rad för rad och kontrollera. Jag löste det genom att flytta in raden i try-blocket så den bara körs
vid lyckade utfall.

### 🪙 Generics
Repository-klassen är generisk och återanvändningsbar eftersom den går att återanvända i exempelvis ett system där
bilar ska läggas till i ett register, det ska gå att få ett utdrag på alla bilarna i hallen samt kanske sortera på 
märke (Volvo/BMW osv). Det skulle även funka om man exempelvis vill lägga till en frimärkessamling med vissa attribut,
kan man få en sammanställning på den totala samlingen och även sortera ut frimärken efter inlagda attribut.

### 🪙 Stream API
Jag ska vara helt ärlig och säga att jag kämpar med att skriva denna typ av kod för det är så mycket som händer på
litet antal tecken, jag har haft github-commits uppe från föreläsningarna. Däremot är det lättare för mig
att konceptuellt förstå vad koden ska göra. Det blir mer text-form och tydligare med lambda-uttrycken ( -> ). Som 
nämndes på en föreläsning läser man koden tydligare för den nästan förklarar _vad_ koden ska göra. Däremot gör 
avsaknaden av variabler, parametrar att jag personligen (som nybörjare) tycker det är svårt att se hur de kedjar fast 
de olika operationerna med varandra.

_Tove Westberg, JUV26D_