/**
 * Created by jeffrey on 1/30/16.
 */
var HomePage = React.createClass({
 render: function() {
     return (
         <div className="row">

             <div className="col-sm-5 col-sm-offset-3">
                 <h1 className="text-center">Upcoming Events!</h1>
                 <EventList/>
             </div>
         </div>
     );
 }
});

ReactDOM.render(<HomePage />, document.getElementById('event_div'));