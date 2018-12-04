<file>
 
Dear <%= firstname %> $lastname,

We <% if (accepted) print 'are pleased' else print 'regret' %> \
to inform you that your paper entitled
 '$title' was ${ accepted ? 'accepted' : 'rejected' }.

    The conference committee.
 
    ${values.each { println it} }

<% values.each { %>
    111****************** ${it}
    <% } %>

</file>