# Elva

Elva is a generic tokenizer written in Java that can be used for basically anything.

The goal of this project was to exercise my Java skills.

Currently recognized tokens:
- Numbers (floats and wholes) (positive and negative)
- Parenthesis
- Common math operations (+, -, *, /, =)
- Commas
- Identifiers (can include digits and underscores)
- Whitespace
- EOF
- Any other unrecognized token is saved as an "UNKNOWN" token

Tokens, when stringified look like standard XML like tags.  
So, for example the input `x = y * 5` would result in the following tokens:
```xml
<IDENT start="1" end="1">x</IDENT>
<WHITESPACE start="2" end="2"/>
<EQUALS start="3" end="3"/>
<WHITESPACE start="4" end="4"/>
<IDENT start="5" end="5">y</IDENT>
<WHITESPACE start="6" end="6"/>
<MULTIPLY start="7" end="7"/>
<WHITESPACE start="8" end="8"/>
<NUMBER start="9" end="9">5</NUMBER>
<EOF start="9" end="9"/>
```

The tokenizer is tested with unit tests.