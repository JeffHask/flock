/**
 * Created by Thomas on 2/9/2016.
 */
var ViewAccount = React.createClass({
    mixins: [Reflux.connect(UserStore, 'userInformation')],
    getInitialState: function() {
        return {userInformation : undefined};
    },
    componentDidMount: function() {
        console.log('componentMounted');
        UserActions.getUserInformation();
    },
    render: function () {
        if (this.state.userInformation !== undefined) {
            return (
                <div>
                    <h1 className="text-center">
                        {this.state.userInformation.firstName} {this.state.userInformation.lastName}
                    </h1>
                    <h2 className="text-center">
                        {this.state.userInformation.email}
                    </h2>
                    <h2 className="text-center">
                        {this.state.userInformation.description}
                    </h2>
                    <h2 className="text-center">
                        {this.state.userInformation.currentCity}
                    </h2>
                </div>
            );
        } else {
            return (
                <h1 className="text-center">Loading <i className="fa fa-spin fa-refresh"/> </h1>
            )
        }
    }
});

ReactDOM.render(<ViewAccount />, document.getElementById('view_account'));