
Document.App = {
    renderResults: function(result) {
       var output = $('#output'),
       resultContent = '';

       // Should render a template with handlebars here instead for better separation.
       resultContent += '<table><tr><th>Duration</th><th>Values</th></tr>';
       for (var index = 0; index < result.length; index++) {
           resultContent += '<tr class="result">';
           resultContent += '<td class="duration">';
           resultContent += result[index].duration;
           resultContent += '</td>';
           resultContent += '<td class="values">';
           resultContent += result[index].values;
           resultContent += '</td>';
           resultContent += '</tr>';
       }
       resultContent += '</table>';

       output.append(resultContent);
    },
    fetchResults: function() {
        $.get(url='result').done(Document.App.renderResults)
    },
    sort: function() {
        $.post({
        url: 'sort',
        success: function(result, message, jqXHR) {
            Document.App.renderResults(result);
        },
        data: JSON.stringify({ values: $('#values').val() }),
        dataType: "json",
        processData:false,
        contentType: "application/json; charset=utf-8"
        });
    }
};

$('#sortButton').click(function() {
    Document.App.sort();
});

Document.App.fetchResults();